export interface NativeCompassPlugin {
  getCurrentHeading(): Promise<{ value: number }>;
}
