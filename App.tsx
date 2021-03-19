import React, { useState } from 'react';
import { Button, View } from 'react-native';
import { cancelJobById, scheduleJob } from './ReactNativeWorkManager';

export default function App() {
  const [jobId, setJobId] = useState<string | undefined>(undefined);

  async function onScheduleTask() {
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
