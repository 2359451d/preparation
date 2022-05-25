import asyncio
from concurrent.futures import ThreadPoolExecutor

# pool
executor = ThreadPoolExecutor(5)

def generate_userprofile(number): #async开头，定义协程函数
    print('userprofile ',number)
    # await asyncio.sleep(1) # 模拟IO操作

def generate_user(number):
    # 某线程内子任务：await userprofile完成后back本任务
    loop = asyncio.new_event_loop()
    generate_userprofile(number)
    print('user',number)

async def generate_other_independent_object(id):
    print('other',id)

# main thread
async def main():
    loop = asyncio.get_running_loop()

    for i in range(10):
        fs = loop.run_in_executor(executor, generate_user, i)
    other_task = loop.create_task(generate_other_independent_object(1))
    await fs
    await other_task

if __name__ == '__main__':
    asyncio.run(main())