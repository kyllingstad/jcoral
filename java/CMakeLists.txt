cmake_minimum_required(VERSION 3.0.0)

set(package "no.viproma")

# Java classes with native code
set(nativeClasses
    "${package}.coral.fmi.FMU"
    "${package}.coral.fmi.Importer"
    "${package}.coral.master.Execution"
    "${package}.coral.master.ProviderCluster"
    "${package}.coral.slave.InProcessRunner"
    "${package}.coral.slave.InstanceFactory"
    "${package}.coral.slave.OpaqueInstance"
)

# Java classes without native code
set(normalClasses
    "${package}.coral.Logging"
    "${package}.coral.fmi.FMIVersion"
    "${package}.coral.master.AddedSlave"
    "${package}.coral.master.EntityNotFoundException"
    "${package}.coral.master.ExecutionOptions"
    "${package}.coral.master.ModelBuilder"
    "${package}.coral.master.ModelConstructionException"
    "${package}.coral.master.ModelSlaveMap"
    "${package}.coral.master.ScenarioBuilder"
    "${package}.coral.master.ScenarioEvent"
    "${package}.coral.master.SimulationProgressMonitor"
    "${package}.coral.master.SlaveConfig"
    "${package}.coral.model.Causality"
    "${package}.coral.model.DataType"
    "${package}.coral.model.ScalarValue"
    "${package}.coral.model.SlaveID"
    "${package}.coral.model.SlaveTypeDescription"
    "${package}.coral.model.Variability"
    "${package}.coral.model.VariableDescription"
    "${package}.coral.model.Variable"
    "${package}.coral.model.VariableSetting"
    "${package}.coral.net.SlaveLocator"
    "${package}.coral.provider.SlaveProviderProcessBuilder"
    "${package}.coral.slave.Instance"
    "${package}.coral.slave.OpaqueInstance"
    "${package}.util.ProcessCapture"
    "${package}.util.ProcessOutput"
)

# ==============================================================================
# Target: jar
# ==============================================================================

set(javaFiles)
set(classFiles)
foreach(class ${nativeClasses} ${normalClasses})
    string(REPLACE "." "/" s "${class}")
    list(APPEND javaFiles "${CMAKE_CURRENT_SOURCE_DIR}/${s}.java")
    list(APPEND classFiles "classes/${s}.class")
endforeach()
add_custom_command(
    OUTPUT ${classFiles}
    COMMAND "${CMAKE_COMMAND}" "-E" "make_directory" "classes"
    COMMAND "${Java_JAVAC_EXECUTABLE}" "-d" "classes" "-Xlint:all,-try" ${javaFiles}
    DEPENDS ${javaFiles}
    VERBATIM
)

set(jarFilename "jcoral.jar")
add_custom_command(
    OUTPUT "${jarFilename}"
    COMMAND "${Java_JAR_EXECUTABLE}" "cf" "${CMAKE_CURRENT_BINARY_DIR}/${jarFilename}" "*"
    WORKING_DIRECTORY "classes"
    DEPENDS ${classFiles}
)

add_custom_target(jar DEPENDS "${jarFilename}")
set_target_properties(jar PROPERTIES
    ARCHIVE_OUTPUT_NAME "${jarFilename}"
    ARCHIVE_OUTPUT_DIRECTORY "${CMAKE_CURRENT_BINARY_DIR}"
)
install(
    FILES "${CMAKE_CURRENT_BINARY_DIR}/${jarFilename}"
    DESTINATION "${jarInstallDir}"
)

# ==============================================================================
# Target: headers
# ==============================================================================

# jniHeaderDir is defined in root CMakeLists.txt
file(MAKE_DIRECTORY "${jniHeaderDir}")

set(headerFiles)
foreach(class ${nativeClasses})
    string(REPLACE "." "_" s "${class}")
    set(h "${jniHeaderDir}/${s}.h")
    list(APPEND headerFiles "${h}")
    # Note: We need -force in the following command because javah only
    # regenerates headers for classes which have been updated, while we
    # have specified that it should depend on the entire JAR file.
    # If we don't use -force, some headers will always be older than the
    # JAR file, and their command will consequently always be run.
    add_custom_command(
        OUTPUT ${h}
        COMMAND "${Java_JAVAH_EXECUTABLE}" "-force" "-d" "${jniHeaderDir}"
            "-classpath" "${jarFilename}" "${class}"
        DEPENDS ${jarFilename}
        VERBATIM
    )
endforeach()
add_custom_target(headers DEPENDS ${headerFiles})

# ==============================================================================
# Target: doc
# ==============================================================================

set(docDir "${CMAKE_BINARY_DIR}/javadoc")
add_custom_command(
    OUTPUT "${docDir}"
    COMMAND "${Java_JAVADOC_EXECUTABLE}" "-d" "${docDir}" "-public"
        "-sourcepath" "${CMAKE_CURRENT_SOURCE_DIR}"
        "-link" "http://docs.oracle.com/javase/7/docs/api"
        "-subpackages" "${package}"
    DEPENDS ${javaFiles}
    VERBATIM
)
add_custom_target(doc ALL DEPENDS "${docDir}")
install(DIRECTORY "${docDir}/" DESTINATION "${docInstallDir}/api")
