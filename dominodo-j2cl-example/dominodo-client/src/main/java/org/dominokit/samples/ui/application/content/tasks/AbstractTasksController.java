package org.dominokit.samples.ui.application.content.tasks;

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.event.ShowPopUpEvent;
import elemental2.dom.HTMLElement;
import org.dominokit.samples.*;
import org.dominokit.samples.event.RefreshEvent;

import java.util.Date;
import java.util.List;

public abstract class AbstractTasksController<C extends IAbstractTasksComponent>
    extends AbstractComponentController<DominoDoContext, C, HTMLElement>
    implements IAbstractTasksComponent.Controller {
  
  public AbstractTasksController() {
  }
  
  @Override
  public void start() {
    this.handlerRegistrations.compose(this.eventBus.addHandler(RefreshEvent.TYPE,
                                                               e -> this.component.edit(this.context.getTasksRepository()
                                                                                                    .listAll(),
                                                                                        e.isAnimate())));
    this.component.edit(this.getTasks(),
                        true);
  }
  
  @Override
  public void doOnEditTask(Task task) {
    this.eventBus.fireEvent(ShowPopUpEvent.show("TaskEditor")
                                          .using("function",
                                                 Constants.FUNCTION_EDIT)
                                          .using("id",
                                                 task.getId()));
  }
  
  @Override
  public void onTagSelected(Task task,
                            String tag) {
    //TODO ...
    //    task.getTags()
    //        .remove(tag);
    //    this.context.getTasksRepository()
    //                .updateTask(task);
    //    this.component.edit(this.context.getTasksRepository()
    //                                    .listAll(),
    //                        false);
  }
  
  @Override
  public void doOnTaskDelete(Task task) {
    this.context.getTasksRepository()
                .removeTask(task);
    this.component.edit(this.context.getTasksRepository()
                                    .listAll(),
                        false);
  }
  
  @Override
  public void doOnTaskPriorityChange(Task task) {
    if (Priority.IMPORTANT.equals(task.getPriority())) {
      task.setPriority(Priority.NORMAL);
    } else {
      task.setPriority(Priority.IMPORTANT);
    }
    this.context.getTasksRepository()
                .updateTask(task);
  }
  
  @Override
  public void doOnResolved(Task task) {
    task.setStatus(Status.COMPLETED);
    this.context.getTasksRepository()
                .updateTask(task);
    this.component.edit(this.context.getTasksRepository()
                                    .listAll(),
                        false);
  }
  
  @Override
  public void doOnUnResolved(Task task) {
    task.setStatus(Status.ACTIVE);
    this.context.getTasksRepository()
                .updateTask(task);
    this.component.edit(this.context.getTasksRepository()
                                    .listAll(),
                        false);
  }
  
  @Override
  public void doOnUpdateDueDate(Task task,
                                Date date) {
    task.setDueDate(date);
    this.context.getTasksRepository()
                .updateTask(task);
  }
  
  public abstract List<Task> getTasks();
  
}
