package org.dominokit.samples.ui.application.content.tasks;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import org.dominokit.domino.ui.animations.Animation;
import org.dominokit.domino.ui.animations.Transition;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.header.BlockHeader;
import org.dominokit.domino.ui.icons.Icons;
import org.dominokit.domino.ui.layout.EmptyState;
import org.dominokit.domino.ui.style.Color;
import org.dominokit.samples.Task;
import org.dominokit.samples.ui.widget.TaskComponent;
import org.dominokit.samples.ui.widget.TaskComponent.TaskComponentDelegate;
import org.jboss.elemento.Elements;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public abstract class AbstractTasksComponent<C extends IAbstractTasksComponent.Controller>
    extends AbstractComponent<IAbstractTasksComponent.Controller, HTMLElement>
    implements IAbstractTasksComponent {
  
  Column         column;
  HTMLDivElement container;
  int            delay    = 100;
  int            DURATION = 400;
  
  public AbstractTasksComponent() {
  }
  
  @Override
  public void render() {
    container = Elements.div()
                        .element();
    initElement(container);
  }
  
  @Override
  public void edit(List<Task> models,
                   boolean animate) {
    if (!Objects.isNull(this.column)) {
      this.column.remove();
    }
    this.column = Column.span8()
                        .offset2();
    container.appendChild(Row.create()
                             .appendChild(this.column)
                             .element());
    this.column.appendChild(BlockHeader.create(getController().doGetTitle()));
    if (models.isEmpty()) {
      this.column.appendChild(EmptyState.create(Icons.ALL.event_available())
                                        .setIconColor(Color.GREY_LIGHTEN_1)
                                        .setTitle("No tasks found")
                                        .setDescription("If you are a developer then something wrong, you must have something to do!")
                                        .styler(style -> style.setMarginTop("10%")));
    } else {
      // TODO remove childs first ...
      models.forEach(m -> {
        TaskComponent taskComponent = TaskComponent.create(m,
                                                           new TaskComponentDelegate() {
          
                                                             @Override
                                                             public void onResolved(Task task) {
                                                               getController().doOnResolved(task);
                                                             }
          
                                                             @Override
                                                             public void onEditTask(Task task) {
                                                               getController().doOnEditTask(task);
                                                             }
          
                                                             @Override
                                                             public void onTagSelected(Task task,
                                                                                       String tag) {
                                                               getController().onTagSelected(task,
                                                                                             tag);
                                                             }
          
                                                             @Override
                                                             public void onTaskDelete(Task task) {
                                                               getController().doOnTaskDelete(task);
                                                             }
          
                                                             @Override
                                                             public void onTaskPriorityChange(Task task) {
                                                               getController().doOnTaskPriorityChange(task);
                                                             }
          
                                                             @Override
                                                             public void onUnResolve(Task task) {
                                                               getController().doOnUnResolved(task);
                                                             }
          
                                                             @Override
                                                             public void onUpdateDueDate(Task task,
                                                                                         Date date) {
                                                               getController().doOnUpdateDueDate(task,
                                                                                                 date);
                                                             }
                                                           });
        if (animate) {
          //          taskComponent.collapse();
          this.column.appendChild(taskComponent);
          Animation.create(taskComponent)
                   .delay(delay)
                   //                   .beforeStart(component -> taskComponent.expand())
                   .duration(DURATION)
                   .transition(Transition.SLIDE_IN_UP)
                   .animate();
          
          delay    = delay + DURATION;
          DURATION = 200;
        } else {
          this.column.appendChild(taskComponent);
        }
      });
    }
  }
  
  @Override
  @SuppressWarnings("unchecked")
  public C getController() {
    return (C) super.getController();
  }
  
}
