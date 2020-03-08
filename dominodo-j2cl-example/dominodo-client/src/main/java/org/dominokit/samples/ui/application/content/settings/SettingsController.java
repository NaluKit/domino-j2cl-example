package org.dominokit.samples.ui.application.content.settings;

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import elemental2.dom.HTMLElement;
import org.dominokit.samples.DominoDoContext;

@Controller(route = "/application",
            selector = "setttings",
            component = SettingsComponent.class,
            componentInterface = ISettingsComponent.class)
public class SettingsController
    extends AbstractComponentController<DominoDoContext, ISettingsComponent, HTMLElement>
    implements ISettingsComponent.Controller {
  
  public SettingsController() {
  }
  
}
