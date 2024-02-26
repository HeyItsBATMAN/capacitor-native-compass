import { WebPlugin } from '@capacitor/core';

import type { NativeCompassPlugin } from './definitions';

export class NativeCompassWeb extends WebPlugin implements NativeCompassPlugin {
  async getCurrentHeading(): Promise<{ value: number }> {
    return Promise.resolve({ value: -1 });
  }
}
