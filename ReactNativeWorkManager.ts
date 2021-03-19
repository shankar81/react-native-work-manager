import { NativeModules } from 'react-native';
import { ScheduleParams } from './types';

const { SchedulingMaster } = NativeModules;

function scheduleJob(params: ScheduleParams): Promise<string> {
  return new Promise((resolve, _) => {
    SchedulingMaster.setUpWorker(
      params.uniqueName,
      params.type,
      params.initialDelay,
      params.intervalDelayUnit,
      params.repeatInterval,
      params.intervalUnit,
      params.notificationTitle,
      params.notificationDesc,
      params.notificationColor,
      (id: string) => {
        resolve(id);
      },
    );
  });
}

function getWorkInfoById(jobId: string | undefined) {
  if (jobId) {
    SchedulingMaster.getWorkInfoById(jobId, (result: boolean) =>
      console.log(result),
    );
  }
}

function cancelJobById(jobId: string | undefined) {
  if (jobId) {
    SchedulingMaster.cancelJobById(jobId, (result: boolean) =>
      console.log(result),
    );
  }
}

export { scheduleJob, cancelJobById, getWorkInfoById };
