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
