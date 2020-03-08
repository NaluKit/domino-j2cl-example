package org.dominokit.samples.ui.application.content.settings;

import com.github.nalukit.nalu.client.component.IsComponent;
import elemental2.dom.HTMLElement;

public interface ISettingsComponent
    extends IsComponent<ISettingsComponent.Controller, HTMLElement> {
  
  interface Controller
      extends IsComponent.Controller {
    
  }
  
}
