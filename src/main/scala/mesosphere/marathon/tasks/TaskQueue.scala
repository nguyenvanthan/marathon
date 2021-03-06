package mesosphere.marathon.tasks

import java.util.concurrent.LinkedBlockingQueue
import mesosphere.marathon.api.v1.AppDefinition
import scala.collection.JavaConverters._

/**
 * Utility class to stage tasks before they get scheduled
 *
 * @author Tobi Knaup
 */

class TaskQueue {

  val queue = new LinkedBlockingQueue[AppDefinition]()

  def poll() =
    queue.poll()

  def add(app: AppDefinition) =
    queue.add(app)

  /**
   * Number of tasks in the queue for the given app
   *
   * @param app The app
   * @return count
   */
  def count(app: AppDefinition): Int = {
    queue.asScala.count(_.id == app.id)
  }

  def purge(app: AppDefinition) {
    val toRemove = queue.asScala.filter(_.id == app.id)
    for (app <- toRemove) {
      queue.remove(app)
    }
  }
}
