# react-native-work-manager
Background scheduling for React-Native using WorkManager from Android Jetpack 

Start schduling or cancel the current task by ID


```javascript
import React, { useState } from 'react';
import { Button, View } from 'react-native';
import { cancelJobById, scheduleJob } from './ReactNativeWorkManager';

export default function App() {
  const [jobId, setJobId] = useState<string | undefined>(undefined);

  async function onScheduleTask() {
    /**
      scheduleJob() function returns the ID Promise<String> of the task
      You can use that ID to cancel the job later
      
      timeUnit can be one of the below types
      type TimeUnit = 'MILLISECONDS' | 'SECONDS' | 'MINUTES' | 'HOURS';
    **/
    const id = await scheduleJob({
      uniqueName: 'Some_Unique_name',
      type: 'PERIODIC',
      initialDelay: 10,
      intervalDelayUnit: 'SECONDS',
      repeatInterval: 15,
      intervalUnit: 'MINUTES',
      notificationTitle: 'This is my notification title',
      notificationDesc: 'This is my notification description from JS code',
    });
    setJobId(id);
  }

  function onCancelJob() {
    cancelJobById(jobId);
  }

  return (
    <View>
      <Button title="Set up request" onPress={onScheduleTask} />
      <Button title="Cancel request" onPress={onCancelJob} />
    </View>
  );
}

```

Define the Headless task in index.js file

```javascript

// Register your task here for Headless JS
AppRegistry.registerHeadlessTask('MyTaskInJS', () => require('./MyWork'));

```

MyWork.js 

```javascript
module.exports = async () => {
  console.log('Running in background');
  console.log('Running in background');
  console.log('Running in background');
  console.log('Running in background');
  console.log('Running in background');
  console.log('Running in background');

  const response = await fetch('https://jsonplaceholder.typicode.com/todos/1');
  const json = await response.json();

  console.log(`Fetched from api ${JSON.stringify(json)}`);
};

```

After initial delay of 10 seconds the task will run automatically
and every 15 Minutes interval if PERIODIC is set

![video](https://user-images.githubusercontent.com/46323867/111748098-1c96c380-88b6-11eb-99ef-340869a83b4a.gif)
