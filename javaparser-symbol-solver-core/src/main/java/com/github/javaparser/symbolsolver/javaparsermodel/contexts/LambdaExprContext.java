/*
 * Copyright (C) 2015-2016 Federico Tomassetti
 * Copyright (C) 2017-2020 The JavaParser Team.
 *
 * This file is part of JavaParser.
 *
 * JavaParser can be used either under the terms of
 * a) the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * b) the terms of the Apache License
 *
 * You should have received a copy of both licenses in LICENCE.LGPL and
 * LICENCE.APACHE. Please refer to those files for details.
 *
 * JavaParser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 */

package com.github.javaparser.symbolsolver.javaparsermodel.contexts;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.LambdaExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.resolution.MethodUsage;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedTypeDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedTypeParameterDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedValueDeclaration;
import com.github.javaparser.resolution.types.ResolvedLambdaConstraintType;
import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFactory;
import com.github.javaparser.symbolsolver.logic.FunctionalInterfaceLogic;
import com.github.javaparser.symbolsolver.logic.InferenceContext;
import com.github.javaparser.symbolsolver.model.resolution.SymbolReference;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.model.resolution.Value;
import com.github.javaparser.symbolsolver.model.typesystem.ReferenceTypeImpl;
import com.github.javaparser.symbolsolver.reflectionmodel.MyObjectProvider;
import com.github.javaparser.symbolsolver.resolution.SymbolDeclarator;

import java.util.*;
import java.lang.System.*;

import static com.github.javaparser.symbolsolver.javaparser.Navigator.demandParentNode;

/**
 * @author Federico Tomassetti
 */
public class LambdaExprContext extends AbstractJavaParserContext<LambdaExpr> {

		public static boolean[] branching = new boolean[100];

    public LambdaExprContext(LambdaExpr wrappedNode, TypeSolver typeSolver) {
        super(wrappedNode, typeSolver);
    }

    @Override
    public Optional<Value> solveSymbolAsValue(String name) {
        for (Parameter parameter : wrappedNode.getParameters()) {
						branching[0] = true;
            SymbolDeclarator sb = JavaParserFactory.getSymbolDeclarator(parameter, typeSolver);
            int index = 0;
            for (ResolvedValueDeclaration decl : sb.getSymbolDeclarations()) {
								branching[1] = true;
                if (decl.getName().equals(name)) {
										branching[2] = true;
                    if (demandParentNode(wrappedNode) instanceof MethodCallExpr) {
												System.out.println("METH");
												branching[3] = true;
                        MethodCallExpr methodCallExpr = (MethodCallExpr) demandParentNode(wrappedNode);
                        MethodUsage methodUsage = JavaParserFacade.get(typeSolver).solveMethodAsUsage(methodCallExpr);
                        int i = pos(methodCallExpr, wrappedNode);
                        ResolvedType lambdaType = methodUsage.getParamTypes().get(i);

                        // Get the functional method in order for us to resolve it's type arguments properly
                        Optional<MethodUsage> functionalMethodOpt = FunctionalInterfaceLogic.getFunctionalMethod(lambdaType);
												System.out.println("LAMBDAAA AAAAA");
                        if (functionalMethodOpt.isPresent()){
														branching[4] = true;
                            MethodUsage functionalMethod = functionalMethodOpt.get();
                            InferenceContext inferenceContext = new InferenceContext(MyObjectProvider.INSTANCE);

                            // Resolve each type variable of the lambda, and use this later to infer the type of each
                            // implicit parameter
                            inferenceContext.addPair(lambdaType, new ReferenceTypeImpl(lambdaType.asReferenceType().getTypeDeclaration(), typeSolver));

                            // Find the position of this lambda argument
                            boolean found = false;
                            int lambdaParamIndex;
														System.out.println("LAMBDAAA: " + wrappedNode.getParameters().size());
														System.out.println("LAMBDAAA: " + wrappedNode.getParameters());
                            for (lambdaParamIndex = 0; lambdaParamIndex < wrappedNode.getParameters().size(); lambdaParamIndex++){
																branching[5] = true;
                                if (wrappedNode.getParameter(lambdaParamIndex).getName().getIdentifier().equals(name)){
																		System.out.println("lolwut" + wrappedNode.getParameter(lambdaParamIndex).getName().getIdentifier());
																		branching[6] = true;
                                    found = true;
                                    break;
                                } else {
																		branching[7] = true;
																}
                            }
                            if (!found) {
															branching[8] = true;
															return Optional.empty();
														} else {
															branching[9] = true;
														}

                            // Now resolve the argument type using the inference context
                            ResolvedType argType = inferenceContext.resolve(inferenceContext.addSingle(functionalMethod.getParamType(lambdaParamIndex)));

                            ResolvedLambdaConstraintType conType;
                            if (argType.isWildcard()){
																branching[10] = true;
                                conType = ResolvedLambdaConstraintType.bound(argType.asWildcard().getBoundedType());
                            } else {
																branching[11] = true;
                                conType = ResolvedLambdaConstraintType.bound(argType);
                            }
                            Value value = new Value(conType, name);
                            return Optional.of(value);
                        } else{
														branching[12] = true;
                            return Optional.empty();
                        }
                    } else if (demandParentNode(wrappedNode) instanceof VariableDeclarator) {
												System.out.println("DEC");
												branching[13] = true;
                        VariableDeclarator variableDeclarator = (VariableDeclarator) demandParentNode(wrappedNode);
                        ResolvedType t = JavaParserFacade.get(typeSolver).convertToUsageVariableType(variableDeclarator);
                        Optional<MethodUsage> functionalMethod = FunctionalInterfaceLogic.getFunctionalMethod(t);
                        if (functionalMethod.isPresent()) {
														branching[14] = true;
                            ResolvedType lambdaType = functionalMethod.get().getParamType(index);

                            // Replace parameter from declarator
                            Map<ResolvedTypeParameterDeclaration, ResolvedType> inferredTypes = new HashMap<>();
                            if (lambdaType.isReferenceType()) {
																branching[15] = true;
                                for (com.github.javaparser.utils.Pair<ResolvedTypeParameterDeclaration, ResolvedType> entry : lambdaType.asReferenceType().getTypeParametersMap()) {
																		branching[16] = true;
                                    if (entry.b.isTypeVariable()) {
																				branching[17] = true;
																				if (entry.b.asTypeParameter().declaredOnType()) {
																						branching[18] = true;
							ResolvedType ot = t.asReferenceType().typeParametersMap().getValue(entry.a);
							lambdaType = lambdaType.replaceTypeVariables(entry.a, ot, inferredTypes);
																				} else {
																						branching[19] = true;

																				}
                                    } else if (entry.b.asTypeParameter().declaredOnType()) {
																				branching[20] = true;
																		}
																		else {
																				branching[21] = true;
																		}
                                }
                            } else if (lambdaType.isTypeVariable() && lambdaType.asTypeParameter().declaredOnType()) {
																branching[19] = true;
                                lambdaType = t.asReferenceType().typeParametersMap().getValue(lambdaType.asTypeParameter());
                            } else if (lambdaType.isTypeVariable() && !lambdaType.asTypeParameter().declaredOnType()) {
																branching[20] = true;
                            } else if (!lambdaType.isTypeVariable() && lambdaType.asTypeParameter().declaredOnType()) {
																branching[21] = true;
														}
														else {
																branching[2] = true;
														}

                            Value value = new Value(lambdaType, name);
                            return Optional.of(value);
                        } else {
														branching[23] = true;
                            throw new UnsupportedOperationException();
                        }
                    } else if (demandParentNode(wrappedNode) instanceof ReturnStmt) {
												System.out.println("STMT");
												branching[24] = true;
                        ReturnStmt returnStmt = (ReturnStmt) demandParentNode(wrappedNode);
                        Optional<MethodDeclaration> optDeclaration = returnStmt.findAncestor(MethodDeclaration.class);
                        if (optDeclaration.isPresent()) {
														System.out.println("SUPER 1" + optDeclaration.get());
														branching[25] = true;
                            ResolvedType t = JavaParserFacade.get(typeSolver).convertToUsage(optDeclaration.get().asMethodDeclaration().getType());
                            Optional<MethodUsage> functionalMethod = FunctionalInterfaceLogic.getFunctionalMethod(t);
														System.out.println("SUPER " + t);
														System.out.println("SUPER " + functionalMethod);

                            if (functionalMethod.isPresent()) {
														System.out.println("SUPER2");
																branching[26] = true;
                                ResolvedType lambdaType = functionalMethod.get().getParamType(index);

                                // Replace parameter from declarator
                                Map<ResolvedTypeParameterDeclaration, ResolvedType> inferredTypes = new HashMap<>();
                                if (lambdaType.isReferenceType()) {
																		branching[27] = true;
                                    for (com.github.javaparser.utils.Pair<ResolvedTypeParameterDeclaration, ResolvedType> entry : lambdaType.asReferenceType().getTypeParametersMap()) {
																				branching[28] = true;
                                        if (entry.b.isTypeVariable() && entry.b.asTypeParameter().declaredOnType()) {
																						branching[27] = true;
                                            ResolvedType ot = t.asReferenceType().typeParametersMap().getValue(entry.a);
                                            lambdaType = lambdaType.replaceTypeVariables(entry.a, ot, inferredTypes);
                                        } else {
																						branching[28] = true;
																				}
                                    }
                                } else if (lambdaType.isTypeVariable() && lambdaType.asTypeParameter().declaredOnType()) {
                                    lambdaType = t.asReferenceType().typeParametersMap().getValue(lambdaType.asTypeParameter());
																		branching[30] = true;
                                } else {
																		branching[31] = true;
																}

                                Value value = new Value(lambdaType, name);
                                return Optional.of(value);
                            } else {
																branching[32] = true;
                                throw new UnsupportedOperationException();
                            }
                        } else {
														branching[33] = true;
												}
                    } else {
												branching[34] = true;
                        throw new UnsupportedOperationException();
                    }
                } else {
										branching[35] = true;
								}
                index++;
            }
        }

        // if nothing is found we should ask the parent context
        return getParent().solveSymbolAsValue(name);
    }

    @Override
    public SymbolReference<? extends ResolvedValueDeclaration> solveSymbol(String name) {
        for (Parameter parameter : wrappedNode.getParameters()) {
            SymbolDeclarator sb = JavaParserFactory.getSymbolDeclarator(parameter, typeSolver);
            SymbolReference<ResolvedValueDeclaration> symbolReference = solveWith(sb, name);
            if (symbolReference.isSolved()) {
                return symbolReference;
            }
        }

        // if nothing is found we should ask the parent context
        return getParent().solveSymbol(name);
    }

    @Override
    public SymbolReference<ResolvedTypeDeclaration> solveType(String name) {
        return getParent().solveType(name);
    }

    @Override
    public SymbolReference<ResolvedMethodDeclaration> solveMethod(
            String name, List<ResolvedType> argumentsTypes, boolean staticOnly) {
        return getParent().solveMethod(name, argumentsTypes, false);
    }

    @Override
    public List<Parameter> parametersExposedToChild(Node child) {
        if (child == wrappedNode.getBody()) {
            return wrappedNode.getParameters();
        }
        return Collections.emptyList();
    }

    ///
    /// Protected methods
    ///

    protected final Optional<Value> solveWithAsValue(SymbolDeclarator symbolDeclarator, String name) {
        for (ResolvedValueDeclaration decl : symbolDeclarator.getSymbolDeclarations()) {
            if (decl.getName().equals(name)) {

                throw new UnsupportedOperationException();
            }
        }
        return Optional.empty();
    }

    ///
    /// Private methods
    ///

    private int pos(MethodCallExpr callExpr, Expression param) {
        int i = 0;
        for (Expression p : callExpr.getArguments()) {
            if (p == param) {
                return i;
            }
            i++;
        }
        throw new IllegalArgumentException();
    }
}
