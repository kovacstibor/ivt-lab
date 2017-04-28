package hu.bme.mit.spaceship;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;

public class GT4500Test {

  private GT4500 ship;

  private TorpedoStore primary;
  private TorpedoStore secondary;

  @Before
  public void init(){
    primary = Mockito.mock(TorpedoStore.class);
    secondary = Mockito.mock(TorpedoStore.class);

    this.ship = new GT4500(primary, secondary);
  }

  @Test
  public void fireTorpedos_Single_Success(){
    // Arrange
    Mockito.when(primary.isEmpty()).thenReturn(false);
    Mockito.when(primary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedos(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);

    // Verify
    Mockito.verify(primary, times(1)).isEmpty();
    Mockito.verify(primary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedos_All_Success(){
    // Arrange
    Mockito.when(primary.isEmpty()).thenReturn(false);
    Mockito.when(primary.fire(1)).thenReturn(true);

    Mockito.when(secondary.isEmpty()).thenReturn(false);
    Mockito.when(secondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedos(FiringMode.ALL);

    // Assert
    assertEquals(true, result);

    // Verify
    Mockito.verify(primary, times(1)).isEmpty();
    Mockito.verify(secondary, times(1)).isEmpty();

    Mockito.verify(primary, times(1)).fire(1);
    Mockito.verify(secondary, times(1)).fire(1);
  }

}
