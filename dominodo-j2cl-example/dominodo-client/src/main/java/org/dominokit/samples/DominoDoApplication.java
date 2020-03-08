package org.dominokit.samples;

import com.github.nalukit.nalu.client.application.IsApplication;
import com.github.nalukit.nalu.client.application.annotation.Application;
import com.github.nalukit.nalu.client.application.annotation.Debug;
import com.github.nalukit.nalu.plugin.elemental2.client.DefaultElemental2Logger;

@Application(startRoute = DominoDoRoutes.ROUTE_TASKS_ALL,
             context = DominoDoContext.class)
@Debug(logLevel = Debug.LogLevel.DETAILED,
       logger = DefaultElemental2Logger.class)
interface DominoDoApplication
    extends IsApplication {
  
}
