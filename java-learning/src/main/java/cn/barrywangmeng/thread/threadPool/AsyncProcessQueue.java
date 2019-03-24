package cn.barrywangmeng.thread.threadPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 代表异步执行的队列<br>
 * 执行一些方法，放入异步队列执行<br>
 * 
 * @author 王孛
 * @version 1.0
 */
public class AsyncProcessQueue {

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * Task 包装类<br>
	 * 此类型的意义是记录可能会被 Executor 吃掉的异常<br>
	 */
	public static class TaskWrapper implements Runnable {
		private static final Logger _LOGGER = LoggerFactory.getLogger(TaskWrapper.class);

		private final Runnable gift;

		public TaskWrapper(final Runnable target) {
			this.gift = target;
		}

		@Override
		public void run() {

			// 捕获异常，避免在 Executor 里面被吞掉了
			if (gift != null) {

				try {
					gift.run();
				} catch (Exception e) {
					_LOGGER.error("Wrapped target execute exception.", e);
				}
			}
		}
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * 执行指定的任务
	 * 
	 * @param task
	 * @return
	 */
	public static boolean execute(final Runnable task) {
		return AsyncProcessor.executeTask(new TaskWrapper(task));
	}
}
