# react-native-work-manager
Background scheduling for React-Native using WorkManager from Android Jetpack 

Start schduling or cancel the current task by ID


```javascript
function test() {
 console.log("look ma’, no spaces");
}
```

![image](https://user-images.githubusercontent.com/46323867/111748506-a2b30a00-88b6-11eb-8a0f-b50d13367d21.png)

Define the Headless task in index.js file

![image](https://user-images.githubusercontent.com/46323867/111748708-df7f0100-88b6-11eb-86d4-4a340a2ae572.png)

After initial delay of 10 seconds the task will run automatically
and every 15 Minutes interval if PERIODIC is set

![video](https://user-images.githubusercontent.com/46323867/111748098-1c96c380-88b6-11eb-99ef-340869a83b4a.gif)
