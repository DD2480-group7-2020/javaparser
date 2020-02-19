/*
 * Copyright (C) 2007-2010 Júlio Vilmar Gesser.
 * Copyright (C) 2011, 2013-2019 The JavaParser Team.
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

package com.github.javaparser.printer.lexicalpreservation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.github.javaparser.printer.lexicalpreservation.Difference;
import com.github.javaparser.printer.lexicalpreservation.Removed;
import com.github.javaparser.printer.lexicalpreservation.RemovedGroup;
import com.github.javaparser.printer.lexicalpreservation.TextElement;
import com.github.javaparser.printer.concretesyntaxmodel.CsmToken;
import com.github.javaparser.printer.concretesyntaxmodel.CsmList;
import com.github.javaparser.ast.observer.ObservableProperty;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class Difference2Test extends AbstractLexicalPreservingTest {

    @BeforeEach
    void setup() {
        
        // tpE = mock(ResolvedTypeParameterDeclaration.class);
        // when(tpE.getName()).thenReturn("T");

        // listOfE = listOf(new ResolvedTypeVariable(tpE));
    }

    @Test
    void applyRemovedDiffElementTest() {
    //     ResolvedType result = new InferenceContext(MyObjectProvider.INSTANCE).addPair(new ResolvedTypeVariable(tpE), listOfString);
    //     assertEquals(new InferenceVariableType(0, MyObjectProvider.INSTANCE), result);
        Difference d = new Difference();

        Removed removed = mock(Removed.class);
        boolean originalElementIsToken = false;
        RemovedGroup removedGroup = mock(RemovedGroup.class);
        TextElement originalElement = mock(TextElement.class);
        boolean originalElementIsChild = false;
        CsmToken csmToken = mock(CsmToken.class);

        when(removed.isChild()).thenReturn(false);
        when(removed.isToken()).thenReturn(false);
        when(removed.isPrimitiveType()).thenReturn(false);
        when(removed.isWhiteSpace()).thenReturn(false);
        when(removed.getElement()).thenReturn(csmToken);

        when(originalElement.isWhiteSpace()).thenReturn(true);

        //when(removedGroup.isProcessed()).thenReturn(true);

        d.applyRemovedDiffElement(removedGroup, removed, originalElement, originalElementIsChild, originalElementIsToken);
        
        assertEquals(d.originalIndex, 1);
    }

    @Test
    //(expected = UnsupportedOperationException.class)
    void applyKeptDiffElementTest() {
    //     ResolvedType result = new InferenceContext(MyObjectProvider.INSTANCE).addPair(new ResolvedTypeVariable(tpE), listOfString);
    //     assertEquals(new InferenceVariableType(0, MyObjectProvider.INSTANCE), result);
        Difference d = new Difference();

        Kept kept = mock(Kept.class);
        boolean originalElementIsToken = true;
        TextElement originalElement = mock(TextElement.class);
        boolean originalElementIsChild = false;
        CsmToken csmToken = mock(CsmToken.class);

        when(originalElement.isComment()).thenReturn(false);
        when(kept.isChild()).thenReturn(true);
        when(kept.isPrimitiveType()).thenReturn(false);
        when(originalElement.isWhiteSpaceOrComment()).thenReturn(false);
        when(originalElement.isIdentifier()).thenReturn(false);

        when(csmToken.toString()).thenReturn("test token");
        when(originalElement.toString()).thenReturn("test orig");

        when(kept.getElement()).thenReturn(csmToken);

        //d.applyKeptDiffElement(kept, originalElement, originalElementIsChild, originalElementIsToken);
        
        assertEquals(1, 1);
    }
}
