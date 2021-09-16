package chung.me.zookeeperstudy

import org.apache.zookeeper.Watcher
import org.apache.zookeeper.ZooKeeper
import java.util.concurrent.CountDownLatch

class ZKConnection {
    private lateinit var zoo: ZooKeeper
    private val connectionLatch = CountDownLatch(1)

    fun connect(host: String): ZooKeeper {
        zoo = ZooKeeper(host, 2000) {
            if (it.state == Watcher.Event.KeeperState.SyncConnected) {
                connectionLatch.countDown()
            }
        }
        connectionLatch.await()
        return zoo;
    }

    fun close() {
        zoo.close()
    }
}
