import { WebPlugin } from '@capacitor/core';

import type { NativeCompassPlugin } from './definitions';

export class NativeCompassWeb extends WebPlugin implements NativeCompassPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
