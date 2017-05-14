# invesdwin-context-python
Integrate python functionality with these modules for invesdwin-context 

## Maven

Releases and snapshots are deployed to this maven repository:
```
http://invesdwin.de/artifactory/invesdwin-oss-remote
```

Dependency declaration:
```xml
<dependency>
	<groupId>de.invesdwin</groupId>
	<artifactId>invesdwin-context-r-runtime-py4j</artifactId>
	<version>1.0.0-SNAPSHOT</version>
</dependency>
```
## Runtime Integration Modules

We have a few options available for integrating python:
- **invesdwin-context-r-runtime-jep**: This runtime loads python by dynamically linking its libraries into the java process. It should provide the best performance for single threaded use and is suitable for scenarios where forking processes should be prevented. You can still use CPython Extension libraries like [Numpy](http://www.numpy.org/), [Pandas](http://pandas.pydata.org/) and [scikit-learn](http://scikit-learn.org/stable/). Just make sure to register them as shared modules by calling `de.invesdwin.context.python.runtime.jep.JepProperties.addSharedModule("modulename")` before launching any tasks so you don't get segmentation faults when Jep instances get closed. Also you might have to apply synchronization if needed as described by the documented [Workarounds for CPython Extensions](https://github.com/mrj0/jep/wiki/Workarounds-for-CPython-Extensions). The interpreter instances are reused in a pooled fashion with fixed threads for each instance for compatibility and performance reasons. To install jep on your operating system, export the java home system variable via `export JAVA_HOME="/usr/lib/jvm/default-java/"` and execute `pip install jep`. Please make sure to use the correct jep libraries you compiled against your desired version of python, or else you will get errors (e.g. syntax errors even with correct syntax) when trying to run your scripts. The following system properties can be used for configuring this module:
```properties
de.invesdwin.context.python.runtime.jep.JepProperties.THREAD_POOL_COUNT=${de.invesdwin.context.ContextProperties.CPU_THREAD_POOL_COUNT}
# specify where the libjep.so resides on your computer (which you might normally add to java.library.path manually)
de.invesdwin.context.python.runtime.jep.JepProperties.JEP_LIBRARY_PATH=/usr/local/lib/python3.5/dist-packages/jep/
```
- **invesdwin-context-r-runtime-py4j**: This runtime launches python via the command line and communicates with the process via [py4j](https://www.py4j.org/) over a socket. It is considered the most robust solution since it does not pollute the JVM process because it creates separate processes for python. Also multithreaded performance does not suffer from the [Global Interpreter Lock](https://wiki.python.org/moin/GlobalInterpreterLock) as [it is the case for Jep](https://github.com/mrj0/jep/wiki/Jep-and-the-GIL) since each task is executed in a different process. The python processes are reused in a pooled fashion for performance reasons. Each python process will have its own java side py4j gateway server and they will communicate over automatically discovered ports on your system. For security reasons the communication is currently restricted to localhost, but it could be easily extended to be [secured via TLS](https://www.py4j.org/advanced_topics.html#tls) and allowing communication over multiple computers (just make a feature request or provide your own implementation as a pull request). To install py4j on your operating system, just execute `pip install py4j`. Currently the module provides the following system properties:
```properties
# you can switch to "python3" here for example or provide a different command entirely
de.invesdwin.context.python.runtime.py4j.Py4jProperties.PYTHON_COMMAND=python
```
- **invesdwin-context-r-runtime-jython**: The runtime uses [Jython](http://www.jython.org/) as a pure JVM version of python. If you don't have to use modules that require CPython extensions, this solution has the benefit of being fully embedded without requiring any setup on the operating system. Though this implementation can be slower than the CPython implementation and you cannot benefit from python compilers like [Numba](http://numba.pydata.org/) or [Anaconda Accelerate](https://docs.continuum.io/accelerate/) that can speed up your python code a lot when it runs in CPython by compiling it directly against the CPU and/or GPU.

You are free to choose which integration method you prefer by selecting the appropriate runtime module as a dependency for your application. The `invesdwin-context-python-runtime-contract` module defines interfaces for integrating your python scripts in a way that works with all of the above runtime modules. So you have the benefit of being able to write your python scripts once and easily test against different runtimes in order to: 
- verify that different python versions and runtimes produce the same results
- to measure the performance impact of the different runtime solutions
- to gain flexibility in various deployment scenarios

## Recommended Editors

For working with python we recommend using [PyDev](http://www.pydev.org/) if you are mainly using Eclipse. Editing and running scripts is very comfortable with this plugin.
