package org.dominokit.samples.event;

import org.gwtproject.event.shared.Event;

public class RefreshEvent
    extends Event<RefreshEvent.RefreshChangeHandler> {
  
  public static Type<RefreshChangeHandler> TYPE = new Type<RefreshChangeHandler>();
  private       boolean                    animate;
  
  public RefreshEvent(boolean animate) {
    this.animate = animate;
  }
  
  @Override
  public Type<RefreshChangeHandler> getAssociatedType() {
    return TYPE;
  }
  
  @Override
  protected void dispatch(RefreshChangeHandler handler) {
    handler.onRefreshChange(this);
  }
  
  public boolean isAnimate() {
    return animate;
  }
  
  public interface RefreshChangeHandler {
    
    void onRefreshChange(RefreshEvent event);
    
  }
  
}
