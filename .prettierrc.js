const ionicConfig = require("@ionic/prettier-config").Config;

/** @type {import("prettier").Config} */
module.exports = {
  ...ionicConfig,
  plugins: ["prettier-plugin-java"],
  overrides: [
    {
      files: ["*.java"],
      options: {
        parser: "java",
      },
    },
  ],
};
