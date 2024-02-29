export interface NativeCompassPlugin {
  /**
   * Returns the current compass heading in degrees.
   * On iOS, the heading is updated in the background, and the latest value is returned.
   * On Android, the heading is calculated when the method is called.
   * Not implemented on Web.
   */
  getCurrentHeading(): Promise<{ value: number }>;
}
