function delay(timeout: number): Promise<undefined> {
  return new Promise(resolve => {
    setTimeout(() => resolve(undefined), timeout);
  });
}
export async function load1() {
  console.log("load1 starting")
  await delay(2000);
  console.log("load1 ending")
  return { message: 'result 1' };
}
export async function load2() {
  await delay(3000);
  return { message: 'result 2' };
}
