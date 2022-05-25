import asyncio
import threading

def task():
    for i in range(5):
        time.sleep(1)
        print("task--"+str(i))
    
def run_loop_inside_thread(loop):
    loop.run_forever()

async def main():
    new_loop = asyncio.new_event_loop()
    asyncio.set_event_loop(new_loop)
    loop = asyncio.get_event_loop()
    threading.Thread(target=run_loop_inside_thread, args=(loop,)).start()
    loop.call_soon_threadsafe(task)

    return "finish"

if __name__ == '__main__':
    asyncio.run(main())