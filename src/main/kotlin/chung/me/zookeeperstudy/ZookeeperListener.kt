package chung.me.zookeeperstudy

import org.slf4j.LoggerFactory
import org.springframework.cloud.zookeeper.discovery.watcher.DependencyState
import org.springframework.cloud.zookeeper.discovery.watcher.DependencyWatcherListener
import org.springframework.stereotype.Component

@Component
class ZookeeperListener: DependencyWatcherListener {
    companion object{
        val logger = LoggerFactory.getLogger(this::class.java)
    }
    private val dependencies = mutableSetOf<String>()

    override fun stateChanged(dependencyName: String?, newState: DependencyState?) {
        logger.info(dependencyName)
        logger.info(newState.toString())
        dependencyName?.let { dependencies.add(it) }
    }

    fun getDependencies(): MutableSet<String> {
        return dependencies
    }
}