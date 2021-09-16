package chung.me.zookeeperstudy

import org.apache.zookeeper.CreateMode
import org.apache.zookeeper.ZooDefs
import org.apache.zookeeper.ZooKeeper
import java.nio.charset.Charset

class ZKManagerImpl :ZKManager {
    companion object{
        private lateinit var zkeeper: ZooKeeper
        private lateinit var zkConnection: ZKConnection
    }

    init {
        initialize()
    }

    private fun initialize() {
        zkConnection = ZKConnection()
        zkeeper = zkConnection.connect("localhost")
    }

    fun closeConnection(){
        zkConnection.close()
    }

    override fun create(path: String, data: ByteArray) {
        zkeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT)
    }

    override fun getZNodeData(path: String, watchFlag: Boolean): String {
        val bytes = zkeeper.getData(path, watchFlag, null)
        return String(bytes, Charset.forName("UTF-8"))
    }

    override fun update(path: String, data: ByteArray) {
        val version = zkeeper.exists(path, true).version
        zkeeper.setData(path, data, version)
    }
}
