/**
 * @format
 */

import { AppRegistry } from 'react-native';
import App from './App';
import { name as appName } from './app.json';

AppRegistry.registerComponent(appName, () => App);

// Register your task here for Headless JS
AppRegistry.registerHeadlessTask('MyTaskInJS', () => require('./MyWork'));
