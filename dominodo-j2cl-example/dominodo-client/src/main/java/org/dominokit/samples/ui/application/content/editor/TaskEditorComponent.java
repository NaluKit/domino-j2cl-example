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

package org.dominokit.samples.ui.application.content.editor;

import com.github.nalukit.nalu.client.component.AbstractPopUpComponent;
import org.dominokit.domino.ui.button.Button;
import org.dominokit.domino.ui.datepicker.DateBox;
import org.dominokit.domino.ui.forms.*;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.icons.Icons;
import org.dominokit.domino.ui.modals.ModalDialog;
import org.dominokit.domino.ui.tag.TagsInput;
import org.dominokit.samples.Constants;
import org.dominokit.samples.Priority;
import org.dominokit.samples.Project;
import org.dominokit.samples.Task;
import org.dominokit.samples.tasks.TasksRepository;
import org.dominokit.samples.ui.widget.attachment.FileUploadComponent;
import org.gwtproject.editor.client.Editor;
import org.gwtproject.editor.client.SimpleBeanEditorDriver;
import org.gwtproject.editor.client.annotation.IsDriver;

import java.util.Date;

import static org.jboss.elemento.Elements.h;

public class TaskEditorComponent
    extends AbstractPopUpComponent<ITaskEditorComponent.Controller>
    implements ITaskEditorComponent,
               Editor<Task> {
  
  TextBox           title;
  TextArea          description;
  DateBox           dueDate;
  Select<Priority>  priority;
  Select<Project>   project;
  TagsInput<String> tags;
  private Driver         driver;
  private FieldsGrouping fieldsGrouping = FieldsGrouping.create();
  private ModalDialog    dialog;
  
  public TaskEditorComponent() {
  }
  
  @Override
  public void render() {
    title = TextBox.create("Title")
                   .setRequired(true)
                   .setAutoValidation(true)
                   .groupBy(fieldsGrouping)
                   .setPlaceholder("Task headline")
                   .floating()
                   .setLeftAddon(Icons.ALL.label());
    
    description = TextArea.create("description")
                          .setRequired(true)
                          .setAutoValidation(true)
                          .groupBy(fieldsGrouping)
                          .setPlaceholder("Describe the task")
                          .floating()
                          .setLeftAddon(Icons.ALL.description())
                          .autoSize()
                          .setRows(2);
    
    dueDate = DateBox.create("Due date",
                             new Date())
                     .apply(element -> element.getDatePicker()
                                              .addDateDayClickHandler((date, dateTimeFormatInfo) -> dueDate.close()))
                     .setRequired(true)
                     .setAutoValidation(true)
                     .groupBy(fieldsGrouping)
                     .setHelperText("Should not be in the past.")
                     .setPlaceholder("Pick a Due date")
                     .floating()
                     .setLeftAddon(Icons.ALL.event());
    
    priority = Select.<Priority>create("Priority").appendChild(SelectOption.create(Priority.IMPORTANT,
                                                                                   "Important"))
                                                  .appendChild(SelectOption.create(Priority.NORMAL,
                                                                                   "Normal"))
                                                  .selectAt(1)
                                                  .setRequired(true)
                                                  .setAutoValidation(true)
                                                  .groupBy(fieldsGrouping)
                                                  .setLeftAddon(Icons.ALL.low_priority());
    
    project = Select.<Project>create("Project").appendChild(SelectOption.create(TasksRepository.PROJECTS.get(Constants.DOMINO_UI),
                                                                                Constants.DOMINO_UI))
                                               .appendChild(SelectOption.create(TasksRepository.PROJECTS.get(Constants.GWT),
                                                                                Constants.GWT))
                                               .appendChild(SelectOption.create(TasksRepository.PROJECTS.get(Constants.NALU_MVP),
                                                                                Constants.NALU_MVP))
                                               .appendChild(SelectOption.create(TasksRepository.PROJECTS.get(Constants.MOVIES),
                                                                                Constants.MOVIES))
                                               .setRequired(true)
                                               .setAutoValidation(true)
                                               .groupBy(fieldsGrouping)
                                               .setLeftAddon(Icons.ALL.collections_bookmark())
                                               .selectAt(0);
    
    tags = TagsInput.create("Tags")
                    .floating()
                    .setPlaceholder("Things related to this task")
                    .setLeftAddon(Icons.ALL.bookmark());
    
    dialog = ModalDialog.create(Constants.FUNCTION_ADD.equals(getController().doGetFunction()) ? "Add Task" : "Edit Task")
                        .setAutoClose(false)
                        .styler(style -> style.add("task-modal"))
                        .appendChild(Row.create()
                                        .fullSpan(column -> column.appendChild(title)))
                        .appendChild(Row.create()
                                        .fullSpan(column -> column.appendChild(description)))
                        .appendChild(Row.create()
                                        .fullSpan(column -> column.appendChild(dueDate)))
                        .appendChild(Row.create()
                                        .fullSpan(column -> column.appendChild(priority)))
                        .appendChild(Row.create()
                                        .fullSpan(column -> column.appendChild(project)))
                        .appendChild(Row.create()
                                        .fullSpan(column -> column.appendChild(tags)))
                        .appendChild(Row.create()
                                        .fullSpan(column -> column.appendChild(h(5).textContent("ATTACHMENTS"))
                                                                  .appendChild(FileUploadComponent.create(getController().getTask()))))
                        .appendFooterChild(Button.create(Icons.ALL.clear())
                                                 .linkify()
                                                 .setContent("CANCEL")
                                                 .styler(style -> style.setMinWidth("100px"))
                                                 .addClickListener(evt -> dialog.close()))
                        .appendFooterChild(Button.createPrimary(Icons.ALL.save())
                                                 .setContent("SAVE")
                                                 .styler(style -> style.setMinWidth("100px"))
                                                 .addClickListener(evt -> getController().doOnSave()));
  }
  
  @Override
  public void bind() {
    driver = new TaskEditorComponent_Driver_Impl();
    driver.initialize(this);
  }
  
  @Override
  public void clear() {
    title.clear();
    description.clear();
    dueDate.clear();
    priority.clear();
    project.clear();
    tags.clear();
  }
  
  @Override
  public void edit(Task task) {
    this.driver.edit(task);
  }
  
  @Override
  public Task flush() {
    return this.driver.flush();
  }
  
  @Override
  public void hide() {
    this.dialog.close();
  }
  
  @Override
  public void show() {
    this.dialog.open();
  }
  
  @IsDriver
  interface Driver
      extends SimpleBeanEditorDriver<Task, TaskEditorComponent> {
    
  }
  
}
