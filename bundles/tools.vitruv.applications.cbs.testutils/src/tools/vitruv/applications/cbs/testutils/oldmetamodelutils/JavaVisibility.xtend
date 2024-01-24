package tools.vitruv.applications.cbs.testutils.oldmetamodelutils

/**
 * Enum for the four possible visibilities in java. We created this
 * enum because the java metamodell does not have a explicit value for
 * "package-private".
 *
 * @author Fei
 */
enum JavaVisibility {
    PUBLIC, PRIVATE, PROTECTED, PACKAGE
}
