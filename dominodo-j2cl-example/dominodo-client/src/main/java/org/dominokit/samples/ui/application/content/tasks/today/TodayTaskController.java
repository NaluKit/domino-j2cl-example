package org.dominokit.samples.ui.application.content.tasks.today;

import com.github.nalukit.nalu.client.component.annotation.Controller;
import org.dominokit.samples.DominoDoRoutes;
import org.dominokit.samples.Task;
import org.dominokit.samples.ui.application.content.tasks.AbstractTasksController;

import java.util.List;

@Controller(route = DominoDoRoutes.ROUTE_TASKS_TODAY,
            selector = "content",
            component = TodayTasksComponent.class,
            componentInterface = ITodayTasksComponent.class)
public class TodayTaskController
    extends AbstractTasksController<ITodayTasksComponent>
    implements ITodayTasksComponent.Controller {
  
  public TodayTaskController() {
  }
  
  @Override
  public List<Task> getTasks() {
    return this.context.getTasksRepository()
                       .listTodayTasks();
  }
  
  @Override
  public String doGetTitle() {
    return "Today's Tasks";
  }
  
}
