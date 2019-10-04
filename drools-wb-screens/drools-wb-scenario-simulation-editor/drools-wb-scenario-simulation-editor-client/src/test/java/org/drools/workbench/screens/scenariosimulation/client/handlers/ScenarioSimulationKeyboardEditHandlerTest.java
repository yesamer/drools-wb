/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.workbench.screens.scenariosimulation.client.handlers;

import java.util.Arrays;
import java.util.Collections;

import com.google.gwtmockito.GwtMockitoTestRunner;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.uberfire.ext.wires.core.grids.client.model.GridData;
import org.uberfire.ext.wires.core.grids.client.widget.grid.GridWidget;
import org.uberfire.ext.wires.core.grids.client.widget.layer.GridLayer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(GwtMockitoTestRunner.class)
public class ScenarioSimulationKeyboardEditHandlerTest {

    private ScenarioSimulationKeyboardEditHandler handler;

    @Mock
    private GridLayer gridLayerMock;

    @Mock
    private GridWidget gridWidgetMock;

    @Mock
    private GridData gridDataMock;

    @Before
    public void setUp() throws Exception {
        handler = new ScenarioSimulationKeyboardEditHandler(gridLayerMock);
        when(gridWidgetMock.getModel()).thenReturn(gridDataMock);
    }

    @Test
    public void testMultipleHeaderCells() {
        when(gridDataMock.getSelectedCells()).thenReturn(Collections.emptyList());
        when(gridDataMock.getSelectedHeaderCells()).thenReturn(Arrays.asList(cell(), cell()));

        Assertions.assertThat(handler.isExecutable(gridWidgetMock))
                .as("Multiple header cells can be edited")
                .isTrue();
    }

    @Test
    public void testOneHeaderCells() {
        when(gridDataMock.getSelectedCells()).thenReturn(Collections.emptyList());
        when(gridDataMock.getSelectedHeaderCells()).thenReturn(Collections.singletonList(cell()));

        Assertions.assertThat(handler.isExecutable(gridWidgetMock))
                .as("Single header cell can be edited")
                .isTrue();
    }

    @Test
    public void testNoCells() {
        when(gridDataMock.getSelectedCells()).thenReturn(Collections.emptyList());
        when(gridDataMock.getSelectedHeaderCells()).thenReturn(Collections.emptyList());

        Assertions.assertThat(handler.isExecutable(gridWidgetMock))
                .as("At least one cell needs to be selected")
                .isFalse();
    }

    @Test
    public void testOneDataCell() {
        when(gridDataMock.getSelectedCells()).thenReturn(Collections.singletonList(cell()));
        when(gridDataMock.getSelectedHeaderCells()).thenReturn(Collections.emptyList());

        Assertions.assertThat(handler.isExecutable(gridWidgetMock))
                .as("Single data cell can be edited")
                .isTrue();
    }

    @Test
    public void testTwoDataCells() {
        when(gridDataMock.getSelectedCells()).thenReturn(Arrays.asList(cell(), cell()));
        when(gridDataMock.getSelectedHeaderCells()).thenReturn(Collections.emptyList());

        Assertions.assertThat(handler.isExecutable(gridWidgetMock))
                .as("Multiple data cells can not be edited")
                .isFalse();
    }

    private GridData.SelectedCell cell() {
        return mock(GridData.SelectedCell.class);
    }
}
