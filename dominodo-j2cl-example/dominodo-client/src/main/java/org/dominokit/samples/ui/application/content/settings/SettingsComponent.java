package org.dominokit.samples.ui.application.content.settings;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import elemental2.dom.HTMLElement;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.forms.SwitchButton;
import org.dominokit.domino.ui.grid.flex.FlexItem;
import org.dominokit.domino.ui.grid.flex.FlexLayout;
import org.dominokit.domino.ui.grid.flex.FlexWrap;
import org.dominokit.domino.ui.header.BlockHeader;
import org.dominokit.domino.ui.style.ColorScheme;
import org.dominokit.domino.ui.style.Style;
import org.dominokit.domino.ui.style.Styles;
import org.dominokit.domino.ui.themes.Theme;
import org.jboss.elemento.EventType;

import static org.jboss.elemento.Elements.*;

public class SettingsComponent
    extends AbstractComponent<ISettingsComponent.Controller, HTMLElement>
    implements ISettingsComponent {
  
  public SettingsComponent() {
  }
  
  @Override
  public void render() {
    Card card = Card.create("Settings");
    card.styler(style -> style.setMarginBottom("0px")
                              .setHeight("100%"));
    
    card.appendChild(BlockHeader.create("Themes"))
    
        .appendChild(FlexLayout.create()
                               .setWrap(FlexWrap.WRAP_TOP_TO_BOTTOM)
                               .appendChild(makeThemeSelector(ColorScheme.RED))
                               .appendChild(makeThemeSelector(ColorScheme.PINK))
                               .appendChild(makeThemeSelector(ColorScheme.PURPLE))
                               .appendChild(makeThemeSelector(ColorScheme.DEEP_PURPLE))
                               .appendChild(makeThemeSelector(ColorScheme.INDIGO))
                               .appendChild(makeThemeSelector(ColorScheme.BLUE))
                               .appendChild(makeThemeSelector(ColorScheme.LIGHT_BLUE))
                               .appendChild(makeThemeSelector(ColorScheme.CYAN))
                               .appendChild(makeThemeSelector(ColorScheme.TEAL))
                               .appendChild(makeThemeSelector(ColorScheme.GREEN))
                               .appendChild(makeThemeSelector(ColorScheme.LIGHT_GREEN))
                               .appendChild(makeThemeSelector(ColorScheme.LIME))
                               .appendChild(makeThemeSelector(ColorScheme.YELLOW))
                               .appendChild(makeThemeSelector(ColorScheme.AMBER))
                               .appendChild(makeThemeSelector(ColorScheme.ORANGE))
                               .appendChild(makeThemeSelector(ColorScheme.DEEP_ORANGE))
                               .appendChild(makeThemeSelector(ColorScheme.BROWN))
                               .appendChild(makeThemeSelector(ColorScheme.GREY))
                               .appendChild(makeThemeSelector(ColorScheme.BLUE_GREY))
                               .appendChild(makeThemeSelector(ColorScheme.BLACK)))
        .appendChild(hr())
        .appendChild(BlockHeader.create("Notifications"))
        .appendChild(h(5).textContent("Show notifications")
                         .add(SwitchButton.create()
                                          .setOnTitle("On")
                                          .setOffTitle("Off")
                                          .styler(Style::pullRight)));
    
    initElement(card.element());
  }
  
  private FlexItem makeThemeSelector(ColorScheme colorScheme) {
    return FlexItem.create()
                   .appendChild(div().css("theme-selector",
                                          colorScheme.color()
                                                     .getBackground(),
                                          Styles.default_shadow)
                                     .on(EventType.click,
                                         event -> new Theme(colorScheme).apply()));
  }
  
}
