package org.dominokit.samples;

import com.github.nalukit.nalu.plugin.elemental2.client.NaluPluginElemental2;

public class DominoDo {
  
  public void run() {
    DominoDoApplication application = new DominoDoApplicationImpl();
    application.run(new NaluPluginElemental2());
  }
  
}
