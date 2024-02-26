export interface NativeCompassPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
