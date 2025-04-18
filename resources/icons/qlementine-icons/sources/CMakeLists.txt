# Create the library.
add_library(${PROJECT_NAME} STATIC)
include(CMakePackageConfigHelpers)

file(GLOB_RECURSE QRC_FILES "resources/icons/*.qrc")
file(GLOB_RECURSE HPP_FILES "include/oclero/qlementine/icons/*.hpp")
file(GLOB_RECURSE CPP_FILES "src/icons/*.cpp")

target_sources(${PROJECT_NAME}
  PRIVATE
    ${HPP_FILES}
    ${CPP_FILES}
    ${QRC_FILES}
)

target_include_directories(${PROJECT_NAME}
  PUBLIC
    $<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}/include>
    $<INSTALL_INTERFACE:${CMAKE_INSTALL_INCLUDEDIR}>
  PRIVATE
    $<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}/src>
)

target_link_libraries(${PROJECT_NAME}
  PUBLIC
    Qt::Core
  PRIVATE 
    Qt::Svg)

set_target_properties(${PROJECT_NAME} PROPERTIES
  LINKER_LANGUAGE CXX
  AUTOMOC ON
  AUTORCC ON
  OUTPUT_NAME     ${PROJECT_NAME}
  PROJECT_LABEL   ${PROJECT_NAME}
  SOVERSION       ${PROJECT_VERSION_MAJOR}
  VERSION         ${PROJECT_VERSION_MAJOR}.${PROJECT_VERSION_MINOR}
  DEBUG_POSTFIX   _debug
)

target_compile_features(${PROJECT_NAME} PUBLIC cxx_std_17)

# Install target
configure_package_config_file("${CMAKE_CURRENT_SOURCE_DIR}/../cmake/config.cmake.in"
  "${CMAKE_BINARY_DIR}/cmake/${PROJECT_NAME}Config.cmake"
  INSTALL_DESTINATION "${CMAKE_INSTALL_LIBDIR}/cmake/${PROJECT_NAME}")

write_basic_package_version_file("${CMAKE_BINARY_DIR}/cmake/${PROJECT_NAME}ConfigVersion.cmake"
  VERSION "${PROJECT_VERSION}"
  COMPATIBILITY AnyNewerVersion)

install(TARGETS ${PROJECT_NAME}
  EXPORT "${PROJECT_NAME}Targets"
  LIBRARY DESTINATION "${CMAKE_INSTALL_LIBDIR}"
  ARCHIVE DESTINATION "${CMAKE_INSTALL_LIBDIR}"
  RUNTIME DESTINATION "${CMAKE_INSTALL_BINDIR}"
  INCLUDES DESTINATION "${CMAKE_INSTALL_INCLUDEDIR}")

install(DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}/include/"
  DESTINATION "${CMAKE_INSTALL_INCLUDEDIR}/")

install(EXPORT "${PROJECT_NAME}Targets"
  FILE "${PROJECT_NAME}Targets.cmake"
  NAMESPACE ${PROJECT_NAME}::
  DESTINATION "${CMAKE_INSTALL_LIBDIR}/cmake/${PROJECT_NAME}")

install(FILES
  "${CMAKE_BINARY_DIR}/cmake/${PROJECT_NAME}Config.cmake"
  "${CMAKE_BINARY_DIR}/cmake/${PROJECT_NAME}ConfigVersion.cmake"
  DESTINATION "${CMAKE_INSTALL_LIBDIR}/cmake/${PROJECT_NAME}")

export(EXPORT "${PROJECT_NAME}Targets"
  FILE "${CMAKE_BINARY_DIR}/cmake/${PROJECT_NAME}Targets.cmake"
  NAMESPACE ${PROJECT_NAME}::)
