package org.dominokit.samples.ui.application.content.tasks.nextweek;

import com.github.nalukit.nalu.client.component.annotation.Controller;
import org.dominokit.samples.DominoDoRoutes;
import org.dominokit.samples.Task;
import org.dominokit.samples.ui.application.content.tasks.AbstractTasksController;

import java.util.List;

@Controller(route = DominoDoRoutes.ROUTE_TASKS_NEXT_WEEK,
            selector = "content",
            component = NextWeekTasksComponent.class,
            componentInterface = INextWeekTasksComponent.class)
public class NextWeekTaskController
    extends AbstractTasksController<INextWeekTasksComponent>
    implements INextWeekTasksComponent.Controller {
  
  public NextWeekTaskController() {
  }
  
  @Override
  public List<Task> getTasks() {
    return this.context.getTasksRepository()
                       .listNextWeekTasks();
  }
  
  @Override
  public String doGetTitle() {
    return "Next Week's Tasks";
  }
  
}
