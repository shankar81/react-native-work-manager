import React, { useState } from 'react';
import { Button, View } from 'react-native';
import {
  cancelJobById,
  getWorkInfoById,
  scheduleJob,
} from './ReactNativeWorkManager';

export default function App() {
  const [jobId, setJobId] = useState<string | undefined>(undefined);

  async function onScheduleTask() {
    const id = await scheduleJob({
      uniqueName: 'Some_Unique_name',
      type: 'SINGLE_SHOT',
      initialDelay: 60,
      intervalUnit: 'MINUTES',
      repeatInterval: 15,
      initialUnit: 'SECONDS',
      notificationTitle: 'This is my notification title',
      notificationDesc: 'This is my notification description from JS code',
    });
    setJobId(id);
  }

  function getWorkInfo() {
    getWorkInfoById(jobId);
  }

  function onCancelJob() {
    cancelJobById(jobId);
  }

  return (
    <View>
      <Button title="Set up request" onPress={onScheduleTask} />
      <Button title="Get Work Info" onPress={getWorkInfo} />
      <Button title="Cancel request" onPress={onCancelJob} />
    </View>
  );
}
