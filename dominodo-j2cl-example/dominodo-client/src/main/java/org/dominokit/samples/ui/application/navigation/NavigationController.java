package org.dominokit.samples.ui.application.navigation;

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import elemental2.dom.HTMLElement;
import org.dominokit.samples.DominoDoContext;
import org.dominokit.samples.DominoDoRoutes;
import org.dominokit.samples.Priority;

@Controller(route = "/application",
            selector = "navigation",
            componentInterface = INavigationComponent.class,
            component = NavigationComponent.class)
public class NavigationController
    extends AbstractComponentController<DominoDoContext, INavigationComponent, HTMLElement>
    implements INavigationComponent.Controller {
  
  public NavigationController() {
  }
  
  @Override
  public void start() {
  }
  
  @Override
  public void onAllSelected() {
    this.router.route(DominoDoRoutes.ROUTE_TASKS_ALL);
  }
  
  @Override
  public void onTodaySelected() {
    this.router.route(DominoDoRoutes.ROUTE_TASKS_TODAY);
  }
  
  @Override
  public void onNextWeekSelected() {
    this.router.route(DominoDoRoutes.ROUTE_TASKS_NEXT_WEEK);
  }
  
  @Override
  public void onPrioritySelected(Priority priority) {
    this.router.route(DominoDoRoutes.ROUTE_TASKS_BY_PRIORITY,
                      priority.toString());
  }
  
  @Override
  public void onProjectSelected(String project) {
    this.router.route(DominoDoRoutes.ROUTE_TASKS_BY_PROJECT,
                      project);
  }
  
  @Override
  public void onListResolved() {
    this.router.route(DominoDoRoutes.ROUTE_TASKS_RESOLVED);
  }
  
}
