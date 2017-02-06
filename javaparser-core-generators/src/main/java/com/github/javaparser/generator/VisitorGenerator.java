package com.github.javaparser.generator;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.generator.utils.SourceRoot;
import com.github.javaparser.metamodel.BaseNodeMetaModel;
import com.github.javaparser.metamodel.JavaParserMetaModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static com.github.javaparser.ast.Modifier.PUBLIC;
import static com.github.javaparser.generator.utils.GeneratorUtils.f;

/**
 * Makes it easier to generate visitor classes.
 * It will create missing visit methods on the fly,
 * and will ask you to fill in the bodies of the visit methods.
 */
public abstract class VisitorGenerator extends Generator {
    private final Logger log = LoggerFactory.getLogger(VisitorGenerator.class);
    private final String pkg;
    private final String visitorClassName;
    private final String returnType;
    private final String argumentType;
    private final boolean createMissingVisitMethods;

    protected VisitorGenerator(JavaParser javaParser, SourceRoot sourceRoot, String pkg, String visitorClassName, String returnType, String argumentType, boolean createMissingVisitMethods) {
        super(javaParser, sourceRoot);
        this.pkg = pkg;
        this.visitorClassName = visitorClassName;
        this.returnType = returnType;
        this.argumentType = argumentType;
        this.createMissingVisitMethods = createMissingVisitMethods;
    }

    public final void generate() throws Exception {
        log.info(f("Running %s", getClass().getSimpleName()));

        final CompilationUnit compilationUnit = sourceRoot.parse(pkg, visitorClassName + ".java", javaParser).get();

        Optional<ClassOrInterfaceDeclaration> visitorClassOptional = compilationUnit.getClassByName(visitorClassName);
        if (!visitorClassOptional.isPresent()) {
            visitorClassOptional = compilationUnit.getInterfaceByName(visitorClassName);
        }
        final ClassOrInterfaceDeclaration visitorClass = visitorClassOptional.get();

        JavaParserMetaModel.getNodeMetaModels().stream()
                .filter((baseNodeMetaModel) -> !baseNodeMetaModel.isAbstract())
                .forEach(node -> generateVisitMethodForNode(node, visitorClass, compilationUnit));
        after();
    }

    protected void after() throws Exception {

    }

    private void generateVisitMethodForNode(BaseNodeMetaModel node, ClassOrInterfaceDeclaration visitorClass, CompilationUnit compilationUnit) {
        Optional<MethodDeclaration> visitMethod = visitorClass.getMethods().stream()
                .filter(m -> m.getNameAsString().equals("visit"))
                .filter(m -> m.getParameter(0).getType().toString().equals(node.getTypeName()))
                .findFirst();

        if (visitMethod.isPresent()) {
            generateVisitMethodBody(node, visitMethod.get(), compilationUnit);
        } else if (createMissingVisitMethods) {
            MethodDeclaration methodDeclaration = visitorClass.addMethod("visit", PUBLIC)
                    .addParameter(node.getTypeNameGenerified(), "n")
                    .addParameter(argumentType, "arg")
                    .setType(returnType);
            generateVisitMethodBody(node, methodDeclaration, compilationUnit);
        }
    }

    protected abstract void generateVisitMethodBody(BaseNodeMetaModel node, MethodDeclaration visitMethod, CompilationUnit compilationUnit);
}
