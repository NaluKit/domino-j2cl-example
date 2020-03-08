/*
 * Copyright (c) 2018 - 2019 - Frank Hossfeld
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 *
 */

package org.dominokit.samples.ui.application.navigation;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import elemental2.dom.HTMLElement;
import org.dominokit.domino.ui.icons.Icons;
import org.dominokit.domino.ui.style.Color;
import org.dominokit.domino.ui.tree.Tree;
import org.dominokit.domino.ui.tree.TreeItem;
import org.dominokit.samples.Constants;
import org.dominokit.samples.Priority;
import org.dominokit.samples.ui.application.navigation.INavigationComponent.Controller;

public class NavigationComponent
    extends AbstractComponent<Controller, HTMLElement>
    implements INavigationComponent {
  
  public NavigationComponent() {
  }
  
  @Override
  public void render() {
    Tree navigationTree = Tree.create("ToDo")
                              .apply(element -> element.getRoot()
                                                       .style()
                                                       .add("menu-flow"))
                              .appendChild(TreeItem.create("All Tasks",
                                                           Icons.ALL.inbox())
                                                   .addClickListener(evt -> getController().onAllSelected()))
    
                              .appendChild(TreeItem.create("Today's tasks",
                                                           Icons.ALL.event())
                                                   .addClickListener(evt -> getController().onTodaySelected()))
    
                              .appendChild(TreeItem.create("Next week",
                                                           Icons.ALL.date_range())
                                                   .addClickListener(evt -> getController().onNextWeekSelected()))
    
                              .addSeparator()
    
                              .appendChild(TreeItem.create("Important",
                                                           Icons.ALL.priority_high()
                                                                    .setColor(Color.RED))
                                                   .addClickListener(evt -> getController().onPrioritySelected(Priority.IMPORTANT)))
    
                              .appendChild(TreeItem.create("Normal",
                                                           Icons.ALL.low_priority()
                                                                    .setColor(Color.TEAL))
                                                   .addClickListener(evt -> getController().onPrioritySelected(Priority.NORMAL)))
    
                              .addSeparator()
    
                              .appendChild(TreeItem.create(Constants.GWT,
                                                           Icons.ALL.inbox()
                                                                    .setColor(Color.PINK))
                                                   .addClickListener(evt -> getController().onProjectSelected(Constants.GWT)))
    
                              .appendChild(TreeItem.create("Domino UI",
                                                           Icons.ALL.inbox()
                                                                    .setColor(Color.INDIGO))
                                                   .addClickListener(evt -> getController().onProjectSelected(Constants.DOMINO_UI)))
    
                              .appendChild(TreeItem.create("Nalu project",
                                                           Icons.ALL.inbox()
                                                                    .setColor(Color.BLUE))
                                                   .addClickListener(evt -> getController().onProjectSelected(Constants.NALU_MVP)))
    
                              .appendChild(TreeItem.create("Movies",
                                                           Icons.ALL.inbox()
                                                                    .setColor(Color.BROWN))
                                                   .addClickListener(evt -> getController().onProjectSelected(Constants.MOVIES)))
                              .addSeparator()
                              .appendChild(TreeItem.create("Resolved",
                                                           Icons.ALL.done_all()
                                                                    .setColor(Color.GREEN))
                                                   .addClickListener(evt -> getController().onListResolved()));
    
    initElement(navigationTree.element());
  }
  
}
