package org.dominokit.samples.ui.application.content.tasks.byproject;

import com.github.nalukit.nalu.client.component.annotation.AcceptParameter;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import org.dominokit.samples.DominoDoRoutes;
import org.dominokit.samples.Task;
import org.dominokit.samples.ui.application.content.tasks.AbstractTasksController;

import java.util.List;

@Controller(route = DominoDoRoutes.ROUTE_TASKS_BY_PROJECT,
            selector = "content",
            component = ByProjectTasksComponent.class,
            componentInterface = IByProjectTasksComponent.class)
public class ByProjectTaskController
    extends AbstractTasksController<IByProjectTasksComponent>
    implements IByProjectTasksComponent.Controller {
  
  private String projectName;
  
  public ByProjectTaskController() {
  }
  
  @Override
  public List<Task> getTasks() {
    return this.context.getTasksRepository()
                       .listByProjectName(this.projectName);
  }
  
  @Override
  public String doGetTitle() {
    return "Tasks By Project";
  }
  
  @AcceptParameter("project")
  public void setProject(String project) {
    if (this.context.getTasksRepository()
                    .isValidProjectName(project)) {
      this.projectName = project;
    } else {
      this.component.showErrorMessage(project);
    }
  }
  
  @Override
  public void doRouteHome() {
    this.router.route(DominoDoRoutes.ROUTE_TASKS_ALL);
  }
  
}
