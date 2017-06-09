# invesdwin-context-python
Integrate python functionality with these modules for the [invesdwin-context](https://github.com/subes/invesdwin-context) module system. 

## Maven

Releases and snapshots are deployed to this maven repository:
```
http://invesdwin.de/artifactory/invesdwin-oss-remote
```

Dependency declaration:
```xml
<dependency>
	<groupId>de.invesdwin</groupId>
	<artifactId>invesdwin-context-python-runtime-py4j</artifactId>
	<version>1.0.0-SNAPSHOT</version>
</dependency>
```
## Runtime Integration Modules

We have a few options available for integrating python:
- **invesdwin-context-python-runtime-jep**: This runtime loads python by dynamically linking its libraries into the java process. It should provide the best performance for single threaded use and is suitable for scenarios where forking processes should be prevented. You can still use CPython Extension libraries like [Numpy](http://www.numpy.org/), [Pandas](http://pandas.pydata.org/) and [scikit-learn](http://scikit-learn.org/stable/). Just make sure to register them as shared modules by calling `JepProperties.addSharedModule("modulename")` before launching any tasks so you don't get segmentation faults when Jep instances get closed. Also you might have to apply synchronization if needed as described by the documented [Workarounds for CPython Extensions](https://github.com/mrj0/jep/wiki/Workarounds-for-CPython-Extensions). The interpreter instances are reused in a pooled fashion with fixed threads for each instance for compatibility and performance reasons. To install Jep on your operating system, export the java home system variable via `export JAVA_HOME="/usr/lib/jvm/default-java/"` and execute `pip install jep`. Please make sure to use the correct Jep libraries you compiled against your desired version of python, or else you will get errors (e.g. syntax errors even with correct syntax) when trying to run your scripts. The following system properties can be used for configuring this module:
```properties
de.invesdwin.context.python.runtime.jep.JepProperties.THREAD_POOL_COUNT=${de.invesdwin.context.ContextProperties.CPU_THREAD_POOL_COUNT}
# specify where the libjep.so resides on your computer (which you might normally add to java.library.path manually)
de.invesdwin.context.python.runtime.jep.JepProperties.JEP_LIBRARY_PATH=/usr/local/lib/python3.5/dist-packages/jep/
```
- **invesdwin-context-python-runtime-py4j**: This runtime launches python via the command line and communicates with the process via [Py4J](https://www.py4j.org/) over a socket. It is considered the most robust solution since it does not pollute the JVM process by creating separate processes for python. Also multithreaded performance does not suffer from the [Global Interpreter Lock](https://wiki.python.org/moin/GlobalInterpreterLock) as [it is the case for Jep](https://github.com/mrj0/jep/wiki/Jep-and-the-GIL), since each task is executed in a different process. The python processes are reused in a pooled fashion for performance reasons. Each python process will have its own java side Py4J gateway server and they will communicate over automatically discovered ports on your system. For security reasons the communication is currently restricted to localhost, but it could be easily extended to be [secured via TLS](https://www.py4j.org/advanced_topics.html#tls) and allowing communication over multiple computers (just make a feature request or provide your own implementation as a pull request). To install Py4J on your operating system, just execute `pip install py4j`. Currently the module provides the following system properties:
```properties
# you can switch to "python3" here for example or provide a different command entirely (e.g. "pypy")
de.invesdwin.context.python.runtime.py4j.Py4jProperties.PYTHON_COMMAND=python
```
- **invesdwin-context-python-runtime-jython**: This runtime uses [Jython](http://www.jython.org/) as a pure JVM implementation of python. If you don't have to use modules that require CPython extensions, this solution has the benefit of being fully embedded without requiring any setup on the operating system. This implementation can be slower than the CPython implementation and you cannot benefit from python compilers like [Numba](http://numba.pydata.org/) or [Anaconda Accelerate](https://docs.continuum.io/accelerate/) that can speed up your python code a lot when it runs in CPython by compiling it directly against the CPU and/or GPU. But on the other hand it provides the most flexible integration when you want to call java code from your python scripts.

You are free to choose which integration method you prefer by selecting the appropriate runtime module as a dependency for your application. The `invesdwin-context-python-runtime-contract` module defines interfaces for integrating your python scripts in a way that works with all of the above runtime modules. So you have the benefit of being able to write your python scripts once and easily test against different runtimes in order to: 
- verify that different python versions and runtimes produce the same results
- to measure the performance impact of the different runtime solutions
- to gain flexibility in various deployment scenarios

## Example Code

This is a minimal example of the famous `Hello World!` as a script:

```java
final AScriptTaskPython<String> script = new AScriptTaskPython<String>() {

    @Override
    public void populateInputs(final IScriptTaskInputs inputs) {
	inputs.putString("hello", "World");
    }

    @Override
    public void executeScript(final IScriptTaskEngine engine) {
	//execute this script inline:
	engine.eval("world = \"Hello \" + hello + \"!\"");
	//or run it from a file:
	//engine.eval(new ClassPathResource("HelloWorldScript.py", getClass()));
    }

    @Override
    public String extractResults(final IScriptTaskResults results) {
	return results.getString("world");
    }
};
final String result = script.run(); //optionally pass a specific runner as an argument here
Assertions.assertThat(result).isEqualTo("Hello World!");
```

For more elaborate examples of the python script integration, have a look at the test cases in `invesdwin-context-python-runtime-contract` which are executed in each individual runtime module test suite.

## Using PyPy

[PyPy](https://pypy.org/) is a compatible and [often faster](http://speed.pypy.org/) implementation of python than CPython is. Just use the `invesdwin-context-python-runtime-py4j` module and set the system property `de.invesdwin.context.python.runtime.py4j.Py4jProperties.PYTHON_COMMAND=pypy`. Installation in ubuntu can be done as follows:
```bash
sudo apt-get install pypy
wget https://bootstrap.pypa.io/get-pip.py 
sudo pypy get-pip.py
rm get-pip.py
sudo pypy -m pip install py4j
```

## Recommended Editor

For working with python we recommend using [PyDev](http://www.pydev.org/) if you are mainly using Eclipse. Editing and running scripts is very comfortable with this plugin. If you want to run your scripts from your main application, you can do this easily with `invesdwin-context-python-runtime-py4j` (add this module as a `test` scope dependency) during development (you also need to add a dependecy to the type `test-jar` for the log level to get activated, or alternatively change the log level of `de.invesdwin.context.python.runtime.contract.IScriptTaskRunnerPython` to `DEBUG` on your own). The actual deployment distribution can choose a different runtime then as a hard dependency. You can also remote debug your scripts comfortably with PyDev inside Eclipse by following [this manual](http://www.pydev.org/manual_adv_remote_debugger.html). 
