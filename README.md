# invesdwin-context-python
Integrate python functionality with these modules for the [invesdwin-context](https://github.com/invesdwin/invesdwin-context) module system. All integration modules provide unified bidirectional communication between Java and python. That way you can switch the python provider without having to change your script implementation. See test cases for examples on how to implement your script integrations.

## Maven

Releases and snapshots are deployed to this maven repository:
```
https://invesdwin.de/repo/invesdwin-oss-remote/
```

Dependency declaration:
```xml
<dependency>
	<groupId>de.invesdwin</groupId>
	<artifactId>invesdwin-context-python-runtime-japyb</artifactId>
	<version>1.0.3</version><!---project.version.invesdwin-context-python-parent-->
</dependency>
```
## Runtime Integration Modules

We have a few options available for integrating python:
- **invesdwin-context-python-runtime-japyb**: This uses a forked version of [Jajub](https://github.com/org-arl/jajub/issues/2) to make it significantly faster, make error handling better, improve robustness and make it compatible with python. It talks to the python process via pipes. Errors are detected by checking for specific protocol messages and by parsing stderr for messages. Python instances are pooled which works well for parallelization. This module provides the following configuration options as system properties:
```properties
# you can switch to a different python installation by defining an absolute path here
de.invesdwin.context.python.runtime.japyb.JapyProperties.PYTHON_COMMAND=python3
```
- **invesdwin-context-python-runtime-jep**: This runtime loads python by dynamically linking its libraries into the java process via [Jep](https://github.com/ninia/jep). It should provide the best performance for single threaded use and is suitable for scenarios where forking processes should be prevented. You can still use CPython Extension libraries like [Numpy](http://www.numpy.org/), [Pandas](http://pandas.pydata.org/) and [scikit-learn](http://scikit-learn.org/stable/). Just make sure to register them as shared modules by calling `JepProperties.addSharedModule("modulename")` before launching any tasks so you don't get segmentation faults when Jep instances get closed. Also you might have to apply synchronization if needed as described by the documented [Workarounds for CPython Extensions](https://github.com/mrj0/jep/wiki/Workarounds-for-CPython-Extensions). The interpreter instances are reused in a pooled fashion with fixed threads for each instance for compatibility and performance reasons. To install Jep on your operating system, export the java home system variable via `export JAVA_HOME="/usr/lib/jvm/default-java/"` and execute `pip install jep` or `pip3 install jep`. Please make sure to use the correct Jep libraries you compiled against your desired version of python, or else you will get errors (e.g. syntax errors even with correct syntax) when trying to run your scripts. The following system properties can be used for configuring this module:
```properties
de.invesdwin.context.python.runtime.jep.JepProperties.THREAD_POOL_COUNT=${de.invesdwin.context.ContextProperties.CPU_THREAD_POOL_COUNT}
# specify where the libjep.so resides on your computer (which you might normally add to java.library.path manually)
de.invesdwin.context.python.runtime.jep.JepProperties.JEP_LIBRARY_PATH=/usr/local/lib/python3.6/dist-packages/jep/
```
- **invesdwin-context-python-runtime-libpythonclj**: This library integrates [libpython-clj](https://github.com/clj-python/libpython-clj/issues/191). It is similar to jep though without requiring any additional installations in python. It only supports CPython 3.x and does not provide sandboxed interpreters. The following system properties can be used for configuring this module:
```properties
# you can switch to a different python installation by defining an absolute path here
de.invesdwin.context.python.runtime.libpythonclj.LibpythoncljProperties.PYTHON_COMMAND=python3
```
- **invesdwin-context-python-runtime-python4j**: This library integrates [python4j](https://github.com/eclipse/deeplearning4j/issues/9595). It uses an embedded CPython distribution via [JavaCPP](https://github.com/bytedeco/javacpp-presets/tree/master/cpython). It only supports CPython 3.x and does not provide sandboxed interpreters. The benefit of this integration is that no python installation is required on the host system since it brings its own. You can sideload python modules from a zipped python virtual environment following this [guide](https://deeplearning4j.konduit.ai/python4j/reference/python-path). No additional configuration needed.
- **invesdwin-context-python-runtime-py4j**: This runtime launches python via the command line and communicates with the process via [Py4J](https://www.py4j.org/) over a socket. It is considered the most robust solution since it does not pollute the JVM process by creating separate processes for python. Also multithreaded performance does not suffer from the [Global Interpreter Lock](https://wiki.python.org/moin/GlobalInterpreterLock) as [it is the case for Jep](https://github.com/mrj0/jep/wiki/Jep-and-the-GIL), since each task is executed in a different process. The python processes are reused in a pooled fashion for performance reasons. Each python process will have its own java side Py4J gateway server and they will communicate over automatically discovered ports on your system. For security reasons the communication is currently restricted to localhost, but it could be easily extended to be [secured via TLS](https://www.py4j.org/advanced_topics.html#tls) and allowing communication over multiple computers (just make a feature request or provide your own implementation as a pull request). To install Py4J on your operating system, just execute `pip install py4j` or `pip3 install py4j`. Currently the module provides the following system properties:
```properties
# you can switch to "python3" here for example or provide a different command entirely (e.g. "pypy")
de.invesdwin.context.python.runtime.py4j.Py4jProperties.PYTHON_COMMAND=python
```
- **invesdwin-context-python-runtime-jython**: This runtime uses [Jython](http://www.jython.org/) as a pure JVM implementation of python. If you don't have to use modules that require CPython extensions, this solution has the benefit of being fully embedded without requiring any setup on the operating system. This implementation can be slower than the CPython implementation and you cannot benefit from python compilers like [Numba](http://numba.pydata.org/) or [Anaconda Accelerate](https://docs.continuum.io/accelerate/) that can speed up your python code a lot when it runs in CPython by compiling it directly against the CPU and/or GPU. But on the other hand it provides the most flexible integration when you want to call java code from your python scripts.
- **invesdwin-context-python-runtime-graalpy**: This runtime uses [GraalPy]([http://www.jython.org/](https://www.graalvm.org/python/)) as a pure JVM implementation of python. It is faster than Jython and more up-to-date. You can sideload python modules from a zipped GraalPy virtual environment following this [guide](https://github.com/graalvm/graal-languages-demos/blob/main/graalpy/graalpy-custom-venv-guide/README.md#42-custom-virtual-environment). Then specify the virtual environment via our system property to take care of the Java side. A static initializer could unzip a bundled virtual environment from the jar and override this system property at application launch before the platform is loaded to fully embed a virtual environment similar to the python4j [guide](https://deeplearning4j.konduit.ai/python4j/reference/python-path) for sideloading modules. Currently the module provides the following system properties:
```properties
# you can uncomment the following line and point it to a GraalPy virtual environment to add custom packages to the runtime
#de.invesdwin.context.python.runtime.graalpy.GraalpyProperties.PYTHON_COMMAND=<GRAALPY_VENV_DIR>/bin/python
```
You are free to choose which integration method you prefer by selecting the appropriate runtime module as a dependency for your application. The `invesdwin-context-python-runtime-contract` module defines interfaces for integrating your python scripts in a way that works with all of the above runtime modules. So you have the benefit of being able to write your python scripts once and easily test against different runtimes in order to: 
- verify that different python versions and runtimes produce the same results
- measure the performance impact of the different runtime solutions
- gain flexibility in various deployment scenarios

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

[PyPy](https://pypy.org/) is a compliant and [often faster](http://speed.pypy.org/) implementation of python than CPython is. Just use the `invesdwin-context-python-runtime-py4j` module and set the system property:

```properties
de.invesdwin.context.python.runtime.py4j.Py4jProperties.PYTHON_COMMAND=pypy
```

Installation on ubuntu can be done as follows:
```bash
sudo apt-get install pypy
wget https://bootstrap.pypa.io/get-pip.py 
sudo pypy get-pip.py
rm get-pip.py
sudo pypy -m pip install py4j
```

Or use the `invesdwin-context-python-runtime-japyb` module (no additional python modules required) and set the system property:
```properties
de.invesdwin.context.python.runtime.japyb.JapybProperties.PYTHON_COMMAND=pypy
```

Though as always you should measure the performance for your use cases and scripts before deciding on a specific solution. Also be aware that [CPython extensions might not be fully compatible with PyPy](http://pypy.org/compat.html).

## Avoiding Bootstrap

If you want to use this project without the overhead of having to initialize a [invesdwin-context](https://github.com/invesdwin/invesdwin-context) bootstrap with its spring-context and module configuration, you can disable the bootstrap with the following code before using any scripts:

```java
de.invesdwin.context.PlatformInitializerProperties.setAllowed(false);
```

The above configuration options for the invidiual runtimes can still be provided by setting system properties before calling any script. An example for all of this can be found at: [ScriptingWithoutBootstrapMain.java](https://github.com/invesdwin/invesdwin-context/blob/master/tests/otherproject-noparent-bom-test/src/main/java/com/otherproject/scripting/ScriptingWithoutBootstrapMain.java)

## Recommended Editor

For working with python we recommend using [PyDev](http://www.pydev.org/) if you are mainly using Eclipse. Editing and running scripts is very comfortable with this plugin. If you want to run your scripts from your main application, you can do this easily with `invesdwin-context-python-runtime-japyb` (add this module as a `test` scope dependency) during development (you also need to add a dependecy to the type `test-jar` of `invesdwin-context-python-runtime-contract` for the log level to get activated, or alternatively change the log level of `de.invesdwin.context.python.runtime.contract.IScriptTaskRunnerPython` to `DEBUG` on your own). The actual deployment distribution can choose a different runtime then as a hard dependency. You can also remote debug your scripts comfortably with PyDev inside Eclipse by following [this manual](http://www.pydev.org/manual_adv_remote_debugger.html). 

## Benchmark

Using python inside strategy backtests (part of the closed source trading platform) has the following overhead. We run a backtest (using the historical engine which is slow in processing speed but efficient in memory usage) on ticks for one month. Each tick we call four times into python to calculate a spread and compare it against the one calculated in java:

### Strategy Code
```java
public class PythonStrategy extends StrategySupport {

    private final String instrumentId;
    private IScriptTaskEngine pythonEngine;
    private ITickCache tickCache;
    private int countPythonCalls = 0;
    private Instant start;
    private Instant lastLog;

    public PythonStrategy(final String instrumentId) {
        this.instrumentId = instrumentId;
    }

    @Override
    public void onInit() {
        tickCache = getBroker().getInstrumentRegistry()
                .getInstrumentOrThrow(instrumentId)
                .getDataSource()
                .getTickCache();
    }

    @Override
    public void onStart() {
        pythonEngine = Py4jScriptTaskEnginePython.newInstance();
        //        pythonEngine = JythonScriptTaskEnginePython.newInstance();
        //        pythonEngine = JepScriptTaskEnginePython.newInstance();
	//        pythonEngine = LibpythoncljScriptTaskEnginePython.newInstance();
	//        pythonEngine = Python4jScriptTaskEnginePython.newInstance();
	//        pythonEngine = JapybScriptTaskEnginePython.newInstance();
        start = new Instant();
        lastLog = new Instant();
    }

    @Override
    public void onTickTime() {
        final ATick lastTick = tickCache.getLastTick(null);
        pythonEngine.getInputs().putDouble("ask", lastTick.getAskAbsolute());
        countPythonCalls++;
        pythonEngine.getInputs().putDouble("bid", lastTick.getBidAbsolute());
        countPythonCalls++;
        pythonEngine.eval("spread = abs(ask-bid)");
        countPythonCalls++;
        final double pythonSpread = pythonEngine.getResults().getDouble("spread");
        countPythonCalls++;
        Assertions.checkEquals(lastTick.getSpreadAbsolute(), pythonSpread);
        if (lastLog.isGreaterThan(Duration.ONE_SECOND)) {
            //CHECKSTYLE:OFF
            System.out.println("Python Calls: " + new ProcessedEventsRateString(countPythonCalls, start.toDuration()));
            //CHECKSTYLE:ON
            lastLog = new Instant();
        }
    }

    @Override
    public void onStop() {
        if (pythonEngine != null) {
            pythonEngine.close();
            pythonEngine = null;
        }
    }

}
```

### Test Code
```java
public class PythonStrategyTest extends ATest {

    @Inject
    private HistoricalBacktestRunFactory historical;

    @Test
    public void test() {
        final BacktestRunConfig config = new BacktestRunConfig();
        config.withDataFeedConfig(DataFeedConfig.TICKS);
	config.withSkipDataFeedPut();
        config.withTimeRange(new TimeRange(FDateBuilder.newDate(2010, 1), FDateBuilder.newDate(2010, 2)));
        config.withProgressListener(new LoggingBacktestRunProgressListener("python"));
        final FastStrategyTrigger trigger = new FastStrategyTrigger(new PythonStrategy("EURUSD"));
        historical.run(config, trigger);
        trigger.close();
    }

}
```

### Results
(Intel i9-9900K)
- **Java Only**: 3716.28/ms ticks processed
- **libpython-clj**: 404.95/ms python calls with 94.73/ms ticks processed
  - [Here](https://github.com/clj-python/libpython-clj/issues/191#issuecomment-1003828913) how to keep the GIL locked during the backtest to speed up python calls
    -  614.23/ms python calls with 139.61/ms ticks processed
  - [Here](https://github.com/clj-python/libpython-clj/issues/191#issuecomment-1003827880) a pattern to reuse precompiled functions to reduce python calls per tick
    - 333.32/ms python calls with 271.22/ms ticks processed
- **Jep**: 215.42/ms python calls with 52.96/ms ticks processed
- **Python4J**: 100.78/ms python calls with 24.12/ms ticks processed
  - when keeping the GIL locked during the backtest to speed up python calls
    - 112.86/ms python calls with 26.74/ms ticks processed
- **Py4J-python3**: 29.63/ms python calls with 7463.35/s ticks processed
- **Py4J-pypy**: 29.3/ms python calls with 7371.21/s ticks processed
- **Japyb-python3**: 14.6/ms python calls with 3625.96/s ticks processed
- **Jython**: 2050.49/s python calls with 511.63/s ticks processed 
  - starts with up to ~5900/s python calls but slows down the longer it runs

### Solution
For faster backtests it might be better to reduce the calls to python to as little as possible. Export data from platform, precalculate data in python using some machine learning frameworks, then use an exported file from python with the results during the strategy backtest. This utilizes the full speed of both python and java. The steps can all be automated from the java side using this python integration. During live trading or visual backtests the communication overhead for a tigther integration should be acceptable as long as no high frequency trading is performed. Also the overhead could become acceptable on backtests on higher granular (e.g. daily) bars since there are a lot less data points to be processed or the decision interval for communicating with python is less frequent.

## More Programming Languages

Similar integration modules like this one also exist for the following other programming languages: 

- **R Modules**: Scripting with R
	- https://github.com/invesdwin/invesdwin-context-r 
- **Matlab/Octave/Scilab Modules**: Scripting with Matlab, Octave and Scilab
	- https://github.com/invesdwin/invesdwin-context-matlab
- **Julia Modules**: Scripting with Julia
	- https://github.com/invesdwin/invesdwin-context-julia
- **JVM Languages Modules**: Scripting with JVM Languages
	- https://github.com/invesdwin/invesdwin-context#scripting-modules-for-jvm-languages


## Support

If you need further assistance or have some ideas for improvements and don't want to create an issue here on github, feel free to start a discussion in our [invesdwin-platform](https://groups.google.com/forum/#!forum/invesdwin-platform) mailing list.
