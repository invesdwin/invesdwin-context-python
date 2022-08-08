package de.invesdwin.context.python.runtime.python4j.internal;

import static org.bytedeco.cpython.global.python.PyBool_FromLong;
import static org.bytedeco.cpython.global.python.PyBytes_AsString;
import static org.bytedeco.cpython.global.python.PyDict_Keys;
import static org.bytedeco.cpython.global.python.PyDict_New;
import static org.bytedeco.cpython.global.python.PyDict_SetItem;
import static org.bytedeco.cpython.global.python.PyDict_Type;
import static org.bytedeco.cpython.global.python.PyDict_Values;
import static org.bytedeco.cpython.global.python.PyErr_Occurred;
import static org.bytedeco.cpython.global.python.PyFloat_AsDouble;
import static org.bytedeco.cpython.global.python.PyFloat_FromDouble;
import static org.bytedeco.cpython.global.python.PyImport_ImportModule;
import static org.bytedeco.cpython.global.python.PyIter_Next;
import static org.bytedeco.cpython.global.python.PyList_New;
import static org.bytedeco.cpython.global.python.PyList_SetItem;
import static org.bytedeco.cpython.global.python.PyLong_AsLong;
import static org.bytedeco.cpython.global.python.PyLong_FromLong;
import static org.bytedeco.cpython.global.python.PyObject_GetAttrString;
import static org.bytedeco.cpython.global.python.PyObject_GetItem;
import static org.bytedeco.cpython.global.python.PyObject_GetIter;
import static org.bytedeco.cpython.global.python.PyObject_IsInstance;
import static org.bytedeco.cpython.global.python.PyObject_Size;
import static org.bytedeco.cpython.global.python.PyObject_Str;
import static org.bytedeco.cpython.global.python.PyObject_Type;
import static org.bytedeco.cpython.global.python.PyUnicode_AsEncodedString;
import static org.bytedeco.cpython.global.python.PyUnicode_FromString;
import static org.bytedeco.cpython.global.python.Py_DecRef;
import static org.bytedeco.cpython.global.python.Py_IncRef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.concurrent.NotThreadSafe;

import org.bytedeco.cpython.PyObject;
import org.nd4j.python4j.Python;
import org.nd4j.python4j.PythonException;
import org.nd4j.python4j.PythonGC;
import org.nd4j.python4j.PythonGIL;
import org.nd4j.python4j.PythonObject;
import org.nd4j.python4j.PythonType;

import de.invesdwin.util.collections.Arrays;

@SuppressWarnings({ "rawtypes", "unchecked" })
@NotThreadSafe
public final class ModifiedPythonTypes {

    public static final PythonType<String> STR = new PythonType<String>("str", String.class) {

        @Override
        public String adapt(final Object javaObject) {
            if (javaObject instanceof String) {
                return (String) javaObject;
            }
            throw new PythonException("Cannot cast object of type " + javaObject.getClass().getName() + " to String");
        }

        @Override
        public String toJava(final PythonObject pythonObject) {
            PythonGIL.assertThreadSafe();
            final PyObject repr = PyObject_Str(pythonObject.getNativePythonObject());
            final PyObject str = PyUnicode_AsEncodedString(repr, "utf-8", "~E~");
            final String jstr = PyBytes_AsString(str).getString();
            Py_DecRef(repr);
            Py_DecRef(str);
            return jstr;
        }

        @Override
        public PythonObject toPython(final String javaObject) {
            return new PythonObject(PyUnicode_FromString(javaObject));
        }
    };

    public static final PythonType<Long> INT = new PythonType<Long>("int", Long.class) {
        @Override
        public Long adapt(final Object javaObject) {
            if (javaObject instanceof Number) {
                return ((Number) javaObject).longValue();
            }
            throw new PythonException("Cannot cast object of type " + javaObject.getClass().getName() + " to Long");
        }

        @Override
        public Long toJava(final PythonObject pythonObject) {
            PythonGIL.assertThreadSafe();
            final long val = PyLong_AsLong(pythonObject.getNativePythonObject());
            if (val == -1 && PyErr_Occurred() != null) {
                throw new PythonException("Could not convert value to int: " + pythonObject.toString());
            }
            return val;
        }

        @Override
        public boolean accepts(final Object javaObject) {
            return (javaObject instanceof Integer) || (javaObject instanceof Long);
        }

        @Override
        public PythonObject toPython(final Long javaObject) {
            return new PythonObject(PyLong_FromLong(javaObject));
        }
    };

    public static final PythonType<Double> FLOAT = new PythonType<Double>("float", Double.class) {

        @Override
        public Double adapt(final Object javaObject) {
            if (javaObject instanceof Number) {
                return ((Number) javaObject).doubleValue();
            }
            throw new PythonException("Cannot cast object of type " + javaObject.getClass().getName() + " to Long");
        }

        @Override
        public Double toJava(final PythonObject pythonObject) {
            PythonGIL.assertThreadSafe();
            final double val = PyFloat_AsDouble(pythonObject.getNativePythonObject());
            if (val == -1 && PyErr_Occurred() != null) {
                throw new PythonException("Could not convert value to float: " + pythonObject.toString());
            }
            return val;
        }

        @Override
        public boolean accepts(final Object javaObject) {
            return (javaObject instanceof Float) || (javaObject instanceof Double);
        }

        @Override
        public PythonObject toPython(final Double javaObject) {
            return new PythonObject(PyFloat_FromDouble(javaObject));
        }
    };

    public static final PythonType<Boolean> BOOL = new PythonType<Boolean>("bool", Boolean.class) {

        @Override
        public Boolean adapt(final Object javaObject) {
            if (javaObject instanceof Boolean) {
                return (Boolean) javaObject;
            }
            throw new PythonException("Cannot cast object of type " + javaObject.getClass().getName() + " to Boolean");
        }

        @Override
        public Boolean toJava(final PythonObject pythonObject) {
            PythonGIL.assertThreadSafe();
            final PyObject builtins = PyImport_ImportModule("builtins");
            final PyObject boolF = PyObject_GetAttrString(builtins, "bool");

            final PythonObject bool = new PythonObject(boolF, false).call(pythonObject);
            final boolean ret = PyLong_AsLong(bool.getNativePythonObject()) > 0;
            bool.del();
            Py_DecRef(boolF);
            Py_DecRef(builtins);
            return ret;
        }

        @Override
        public PythonObject toPython(final Boolean javaObject) {
            return new PythonObject(PyBool_FromLong(javaObject ? 1 : 0));
        }
    };

    public static final PythonType<List> LIST = new PythonType<List>("list", List.class) {

        @Override
        public boolean accepts(final Object javaObject) {
            return (javaObject instanceof List || javaObject.getClass().isArray());
        }

        @Override
        public List adapt(final Object javaObject) {
            if (javaObject instanceof List) {
                return (List) javaObject;
            } else if (javaObject.getClass().isArray()) {
                final List<Object> ret = new ArrayList<>();
                if (javaObject instanceof Object[]) {
                    final Object[] arr = (Object[]) javaObject;
                    return new ArrayList<>(Arrays.asList(arr));
                } else if (javaObject instanceof short[]) {
                    final short[] arr = (short[]) javaObject;
                    for (final short x : arr) {
                        ret.add(x);
                    }
                    return ret;
                } else if (javaObject instanceof int[]) {
                    final int[] arr = (int[]) javaObject;
                    for (final int x : arr) {
                        ret.add(x);
                    }
                    return ret;
                } else if (javaObject instanceof byte[]) {
                    final byte[] arr = (byte[]) javaObject;
                    for (final int x : arr) {
                        ret.add(x & 0xff);
                    }
                    return ret;
                } else if (javaObject instanceof long[]) {
                    final long[] arr = (long[]) javaObject;
                    for (final long x : arr) {
                        ret.add(x);
                    }
                    return ret;
                } else if (javaObject instanceof float[]) {
                    final float[] arr = (float[]) javaObject;
                    for (final float x : arr) {
                        ret.add(x);
                    }
                    return ret;
                } else if (javaObject instanceof double[]) {
                    final double[] arr = (double[]) javaObject;
                    for (final double x : arr) {
                        ret.add(x);
                    }
                    return ret;
                } else if (javaObject instanceof boolean[]) {
                    final boolean[] arr = (boolean[]) javaObject;
                    for (final boolean x : arr) {
                        ret.add(x);
                    }
                    return ret;
                } else {
                    throw new PythonException("Unsupported array type: " + javaObject.getClass().toString());
                }

            } else {
                throw new PythonException("Cannot cast object of type " + javaObject.getClass().getName() + " to List");
            }
        }

        @Override
        public List toJava(final PythonObject pythonObject) {
            PythonGIL.assertThreadSafe();
            final List ret = new ArrayList();
            final long n = PyObject_Size(pythonObject.getNativePythonObject());
            if (n < 0) {
                throw new PythonException("Object cannot be interpreted as a List");
            }
            for (long i = 0; i < n; i++) {
                final PyObject pyIndex = PyLong_FromLong(i);
                final PyObject pyItem = PyObject_GetItem(pythonObject.getNativePythonObject(), pyIndex);
                Py_DecRef(pyIndex);
                final PythonType pyItemType = getPythonTypeForPythonObject(new PythonObject(pyItem, false));
                ret.add(pyItemType.toJava(new PythonObject(pyItem, false)));
                Py_DecRef(pyItem);
            }
            return ret;
        }

        @Override
        public PythonObject toPython(final List javaObject) {
            PythonGIL.assertThreadSafe();
            final PyObject pyList = PyList_New(javaObject.size());
            for (int i = 0; i < javaObject.size(); i++) {
                final Object item = javaObject.get(i);
                final PythonObject pyItem;
                final boolean owned;
                if (item instanceof PythonObject) {
                    pyItem = (PythonObject) item;
                    owned = false;
                } else if (item instanceof PyObject) {
                    pyItem = new PythonObject((PyObject) item, false);
                    owned = false;
                } else {
                    pyItem = ModifiedPythonTypes.convert(item);
                    owned = true;
                }
                Py_IncRef(pyItem.getNativePythonObject()); // reference will be stolen by PyList_SetItem()
                PyList_SetItem(pyList, i, pyItem.getNativePythonObject());
                if (owned) {
                    pyItem.del();
                }
            }
            return new PythonObject(pyList);
        }
    };

    public static final PythonType<Map> DICT = new PythonType<Map>("dict", Map.class) {

        @Override
        public Map adapt(final Object javaObject) {
            if (javaObject instanceof Map) {
                return (Map) javaObject;
            }
            throw new PythonException("Cannot cast object of type " + javaObject.getClass().getName() + " to Map");
        }

        @Override
        public Map toJava(final PythonObject pythonObject) {
            PythonGIL.assertThreadSafe();
            final HashMap ret = new HashMap();
            final PyObject dictType = new PyObject(PyDict_Type());
            if (PyObject_IsInstance(pythonObject.getNativePythonObject(), dictType) != 1) {
                throw new PythonException("Expected dict, received: " + pythonObject.toString());
            }

            final PyObject keys = PyDict_Keys(pythonObject.getNativePythonObject());
            final PyObject keysIter = PyObject_GetIter(keys);
            final PyObject vals = PyDict_Values(pythonObject.getNativePythonObject());
            final PyObject valsIter = PyObject_GetIter(vals);
            try {
                final long n = PyObject_Size(pythonObject.getNativePythonObject());
                for (long i = 0; i < n; i++) {
                    final PythonObject pyKey = new PythonObject(PyIter_Next(keysIter), false);
                    final PythonObject pyVal = new PythonObject(PyIter_Next(valsIter), false);
                    final PythonType pyKeyType = getPythonTypeForPythonObject(pyKey);
                    final PythonType pyValType = getPythonTypeForPythonObject(pyVal);
                    ret.put(pyKeyType.toJava(pyKey), pyValType.toJava(pyVal));
                    Py_DecRef(pyKey.getNativePythonObject());
                    Py_DecRef(pyVal.getNativePythonObject());
                }
            } finally {
                Py_DecRef(keysIter);
                Py_DecRef(valsIter);
                Py_DecRef(keys);
                Py_DecRef(vals);
            }
            return ret;
        }

        @Override
        public PythonObject toPython(final Map javaObject) {
            PythonGIL.assertThreadSafe();
            final PyObject pyDict = PyDict_New();
            for (final Object k : javaObject.keySet()) {
                final PythonObject pyKey;
                if (k instanceof PythonObject) {
                    pyKey = (PythonObject) k;
                } else if (k instanceof PyObject) {
                    pyKey = new PythonObject((PyObject) k);
                } else {
                    pyKey = ModifiedPythonTypes.convert(k);
                }
                final Object v = javaObject.get(k);
                final PythonObject pyVal;
                if (v instanceof PythonObject) {
                    pyVal = (PythonObject) v;
                } else if (v instanceof PyObject) {
                    pyVal = new PythonObject((PyObject) v);
                } else {
                    pyVal = ModifiedPythonTypes.convert(v);
                }
                final int errCode = PyDict_SetItem(pyDict, pyKey.getNativePythonObject(),
                        pyVal.getNativePythonObject());
                if (errCode != 0) {
                    final String keyStr = pyKey.toString();
                    pyKey.del();
                    pyVal.del();
                    throw new PythonException("Unable to create python dictionary. Unhashable key: " + keyStr);
                }
                pyKey.del();
                pyVal.del();
            }
            return new PythonObject(pyDict);
        }
    };

    public static final PythonType<byte[]> BYTES = new PythonType<byte[]>("bytes", byte[].class) {
        @Override
        public byte[] toJava(final PythonObject pythonObject) {
            try (PythonGC gc = PythonGC.watch()) {
                if (!(Python.isinstance(pythonObject, Python.bytesType()))) {
                    throw new PythonException("Expected bytes. Received: " + pythonObject);
                }
                final PythonObject pySize = Python.len(pythonObject);
                final byte[] ret = new byte[pySize.toInt()];
                for (int i = 0; i < ret.length; i++) {
                    ret[i] = (byte) pythonObject.get(i).toInt();
                }
                return ret;
            }
        }

        @Override
        public PythonObject toPython(final byte[] javaObject) {
            try (PythonGC gc = PythonGC.watch()) {
                final PythonObject ret = Python.bytes(LIST.toPython(LIST.adapt(javaObject)));
                PythonGC.keep(ret);
                return ret;
            }
        }

        @Override
        public boolean accepts(final Object javaObject) {
            return javaObject instanceof byte[];
        }

        @Override
        public byte[] adapt(final Object javaObject) {
            if (javaObject instanceof byte[]) {
                return (byte[]) javaObject;
            }
            throw new PythonException("Cannot cast object of type " + javaObject.getClass().getName() + " to byte[]");
        }

    };

    public static final PythonType NONE = new NonePythonType();
    private static final List<PythonType> PRIMITIVE_TYPES = Arrays.<PythonType> asList(NONE, STR, INT, FLOAT, BOOL,
            BYTES);
    private static final List<PythonType> COLLECTION_TYPES = Arrays.<PythonType> asList(LIST, DICT);
    private static final List<PythonType> EXTERNAL_TYPES = newExternalTypes();
    private static final List<PythonType> TYPES = newTypes();
    private static final ConcurrentMap<Class<?>, PythonType> JAVACLASS_TYPE = new ConcurrentHashMap<>();
    private static final ConcurrentMap<String, PythonType> PYTHONNAME_TYPE = new ConcurrentHashMap<>();

    private ModifiedPythonTypes() {
    }

    private static List<PythonType> newExternalTypes() {
        final List<PythonType> ret = new ArrayList<>();
        final ServiceLoader<PythonType> sl = ServiceLoader.load(PythonType.class);
        final Iterator<PythonType> iter = sl.iterator();
        while (iter.hasNext()) {
            ret.add(iter.next());
        }
        return ret;
    }

    private static List<PythonType> newTypes() {
        final List<PythonType> ret = new ArrayList<>();
        ret.addAll(PRIMITIVE_TYPES);
        ret.addAll(COLLECTION_TYPES);
        ret.addAll(EXTERNAL_TYPES);
        return ret;
    }

    public static List<PythonType> get() {
        return TYPES;
    }

    public static <T> PythonType<T> get(final String name) {
        for (final PythonType pt : get()) {
            if (pt.getName().equals(name)) { // TODO use map instead?
                return pt;
            }

        }
        throw new PythonException("Unknown python type: " + name);
    }

    public static PythonType getPythonTypeForJavaObject(final Object javaObject) {
        if (javaObject == null) {
            return NONE;
        }

        final Class<?> type = javaObject.getClass();
        final PythonType cachedType = JAVACLASS_TYPE.get(type);
        if (cachedType != null) {
            return cachedType;
        }

        final PythonType newType = newPythonTypeForJavaObject(javaObject);
        JAVACLASS_TYPE.put(type, newType);
        return newType;
    }

    private static PythonType newPythonTypeForJavaObject(final Object javaObject) {
        if (STR.accepts(javaObject)) {
            return STR;
        } else if (INT.accepts(javaObject)) {
            return INT;
        } else if (FLOAT.accepts(javaObject)) {
            return FLOAT;
        } else if (BOOL.accepts(javaObject)) {
            return BOOL;
        } else if (BYTES.accepts(javaObject)) {
            return BYTES;
        } else if (LIST.accepts(javaObject)) {
            return LIST;
        } else if (DICT.accepts(javaObject)) {
            return DICT;
        }
        for (int i = 0; i < EXTERNAL_TYPES.size(); i++) {
            final PythonType type = EXTERNAL_TYPES.get(i);
            if (type.accepts(javaObject)) {
                return type;
            }
        }
        throw new PythonException("Unable to find python type for java type: " + javaObject.getClass());
    }

    public static <T> PythonType<T> getPythonTypeForPythonObject(final PythonObject pythonObject) {
        final PyObject pyType = PyObject_Type(pythonObject.getNativePythonObject());
        try {
            final String pyTypeStr = ModifiedPythonTypes.STR.toJava(new PythonObject(pyType, false));

            final PythonType cachedType = PYTHONNAME_TYPE.get(pyTypeStr);
            if (cachedType != null) {
                return cachedType;
            }

            final PythonType newType = newPythonTypeForPythonObject(pythonObject, pyTypeStr);
            PYTHONNAME_TYPE.put(pyTypeStr, newType);
            return newType;
        } finally {
            Py_DecRef(pyType);
        }

    }

    private static <T> PythonType<T> newPythonTypeForPythonObject(final PythonObject pythonObject,
            final String pyTypeStr) {
        for (final PythonType pt : get()) {
            final String pyTypeStr2 = "<class '" + pt.getName() + "'>";
            if (pyTypeStr.equals(pyTypeStr2)) {
                return pt;
            } else {
                try (PythonGC gc = PythonGC.watch()) {
                    final PythonObject pyType2 = pt.pythonType();
                    if (pyType2 != null && Python.isinstance(pythonObject, pyType2)) {
                        return pt;
                    }
                }

            }
        }
        throw new PythonException("Unable to find converter for python object of type " + pyTypeStr);
    }

    public static PythonObject convert(final Object javaObject) {
        final PythonType pt = getPythonTypeForJavaObject(javaObject);
        return pt.toPython(pt.adapt(javaObject));
    }

}
