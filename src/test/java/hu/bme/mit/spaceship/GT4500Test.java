package hu.bme.mit.spaceship;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
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

  @Test
  public void fireTorpedos_Single_Primary_Success() {
    Mockito.when(primary.isEmpty()).thenReturn(false);
    Mockito.when(primary.fire(1)).thenReturn(true);

    boolean result = ship.fireTorpedos(FiringMode.SINGLE);

    Assert.assertEquals(true, result);

    Mockito.verify(primary, times(1)).isEmpty();
    Mockito.verify(primary, times(1)).fire(1);
    Mockito.verify(secondary, never()).isEmpty();
    Mockito.verify(secondary, never()).fire(1);
  }

  @Test
  public void fireTorpedos_Single_Secondary_Success() {
    Mockito.when(primary.isEmpty()).thenReturn(false);
    Mockito.when(primary.fire(1)).thenReturn(true);

    Mockito.when(secondary.isEmpty()).thenReturn(false);
    Mockito.when(secondary.fire(1)).thenReturn(true);

    ship.fireTorpedos(FiringMode.SINGLE);
    boolean result = ship.fireTorpedos(FiringMode.SINGLE);

    Assert.assertEquals(true, result);

    Mockito.verify(primary, times(1)).isEmpty();
    Mockito.verify(primary, times(1)).fire(1);
    Mockito.verify(secondary, times(1)).isEmpty();
    Mockito.verify(secondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedos_Single_Primary_Empty_Secondary_Success() {
    Mockito.when(primary.isEmpty()).thenReturn(true);

    Mockito.when(secondary.isEmpty()).thenReturn(false);
    Mockito.when(secondary.fire(1)).thenReturn(true);

    boolean result = ship.fireTorpedos(FiringMode.SINGLE);

    Assert.assertEquals(true, result);

    Mockito.verify(primary, times(1)).isEmpty();
    Mockito.verify(primary, never()).fire(1);
    Mockito.verify(secondary, times(1)).isEmpty();
    Mockito.verify(secondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedos_Single_Secondary_Empty_Primary_Success() {
    Mockito.when(primary.isEmpty()).thenReturn(false);
    Mockito.when(primary.fire(1)).thenReturn(true);

    Mockito.when(secondary.isEmpty()).thenReturn(true);

    ship.fireTorpedos(FiringMode.SINGLE);
    boolean result = ship.fireTorpedos(FiringMode.SINGLE);

    Assert.assertEquals(true, result);

    Mockito.verify(primary, times(2)).isEmpty();
    Mockito.verify(primary, times(2)).fire(1);
    Mockito.verify(secondary, times(1)).isEmpty();
    Mockito.verify(secondary, never()).fire(1);
  }

  @Test
  public void fireTorpedos_Single_Primary_And_Secondary_Empty_Fail() {
    Mockito.when(primary.isEmpty()).thenReturn(true);
    Mockito.when(secondary.isEmpty()).thenReturn(true);

    boolean result = ship.fireTorpedos(FiringMode.SINGLE);

    Assert.assertEquals(false, result);

    Mockito.verify(primary, times(1)).isEmpty();
    Mockito.verify(primary, never()).fire(1);
    Mockito.verify(secondary, times(1)).isEmpty();
    Mockito.verify(secondary, never()).fire(1);
  }

  @Test
  public void fireTorpedos_Single_Primary_Fails() {
    Mockito.when(primary.isEmpty()).thenReturn(false);
    Mockito.when(primary.fire(1)).thenReturn(false);

    boolean result = ship.fireTorpedos(FiringMode.SINGLE);

    Assert.assertEquals(false, result);

    Mockito.verify(primary, times(1)).isEmpty();
    Mockito.verify(primary, times(1)).fire(1);
    Mockito.verify(secondary, never()).isEmpty();
    Mockito.verify(secondary, never()).fire(1);
  }

  @Test
  public void fireTorpedos_Single_Secondary_Fails() {
    Mockito.when(primary.isEmpty()).thenReturn(false);
    Mockito.when(primary.fire(1)).thenReturn(true);
    Mockito.when(secondary.isEmpty()).thenReturn(false);
    Mockito.when(secondary.fire(1)).thenReturn(false);

    ship.fireTorpedos(FiringMode.SINGLE);
    boolean result = ship.fireTorpedos(FiringMode.SINGLE);

    Assert.assertEquals(false, result);

    Mockito.verify(primary, times(1)).isEmpty();
    Mockito.verify(primary, times(1)).fire(1);
    Mockito.verify(secondary, times(1)).isEmpty();
    Mockito.verify(secondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedos_All_Primary_Empty() {
    Mockito.when(primary.isEmpty()).thenReturn(true);


    boolean result = ship.fireTorpedos(FiringMode.ALL);
    assertEquals(false, result);

    Mockito.verify(primary, times(1)).isEmpty();
    Mockito.verify(primary, never()).fire(1);
    Mockito.verify(secondary, never()).isEmpty();
    Mockito.verify(secondary, never()).fire(1);
  }

  @Test
  public void fireTorpedos_All_Secondary_Empty() {
    Mockito.when(primary.isEmpty()).thenReturn(false);
    Mockito.when(secondary.isEmpty()).thenReturn(true);

    boolean result = ship.fireTorpedos(FiringMode.ALL);

    Mockito.verify(primary, times(1)).isEmpty();
    Mockito.verify(primary, never()).fire(1);
    Mockito.verify(secondary, times(1)).isEmpty();
    Mockito.verify(secondary, never()).fire(1);
  }

  @Test
  public void fireLasers_Example() {
    // TODO: fireLasers methon is not yet implemented
    boolean result = ship.fireLasers(FiringMode.SINGLE);
    Assert.assertEquals(false, result);
  }

  @Test
  public void fireTorpedos_Single_Secondary_Empty_Primary_Fails_To_Replace() {
    Mockito.when(primary.isEmpty()).thenReturn(false);
    Mockito.when(primary.fire(1)).thenReturn(true);

    Mockito.when(secondary.isEmpty()).thenReturn(true);

    ship.fireTorpedos(FiringMode.SINGLE);
    Mockito.when(primary.isEmpty()).thenReturn(true);

    boolean result = ship.fireTorpedos(FiringMode.SINGLE);

    Assert.assertEquals(false, result);

    Mockito.verify(primary, times(2)).isEmpty();
    Mockito.verify(primary, times(1)).fire(1);
    Mockito.verify(secondary, times(1)).isEmpty();
    Mockito.verify(secondary, never()).fire(1);
  }
}
