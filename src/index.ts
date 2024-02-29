import { registerPlugin } from "@capacitor/core";

import type { NativeCompassPlugin } from "./definitions";

const NativeCompass = registerPlugin<NativeCompassPlugin>("NativeCompass", {
  web: () => import("./web").then((m) => new m.NativeCompassWeb()),
});

export * from "./definitions";
export { NativeCompass };
