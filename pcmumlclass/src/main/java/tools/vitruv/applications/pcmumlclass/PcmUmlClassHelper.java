package tools.vitruv.applications.pcmumlclass;

import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.palladiosimulator.pcm.core.entity.Entity;
import org.palladiosimulator.pcm.repository.*;
import tools.vitruv.applications.util.temporary.other.CorrespondenceRetriever;
import tools.vitruv.change.interaction.UserInteractionOptions;
import tools.vitruv.change.interaction.UserInteractor;

public final class PcmUmlClassHelper {

  public static PrimitiveType mapPcmToUmlPrimitiveType(
          PrimitiveDataType pcmPredefinedPrimitiveType,
          Iterable<PrimitiveType> umlPredefinedPrimitiveTypes) {

    if (pcmPredefinedPrimitiveType == null || pcmPredefinedPrimitiveType.getType() == null) return null;

    String umlTypeName = switch (pcmPredefinedPrimitiveType.getType()) {
      case BOOL -> "Boolean";
      case INT -> "Integer";
      case DOUBLE -> "Real";
      case STRING -> "String";
      case BYTE -> "byte";
      case CHAR -> "char";
      case LONG -> "Real";
      default -> null;
    };

    if (umlTypeName == null) return null;

    for (PrimitiveType umlType : umlPredefinedPrimitiveTypes) {
      if (Objects.equals(umlType.getName(), umlTypeName)) {
        return umlType;
      }
    }
    return null;
  }

  public static PrimitiveDataType mapUmlToPcmPrimitiveType(
          PrimitiveType umlPrimitiveDataType,
          Iterable<PrimitiveDataType> pcmPrimitiveDataTypes) {

    if (umlPrimitiveDataType == null) return null;

    String umlName = umlPrimitiveDataType.getName().toLowerCase();
    PrimitiveTypeEnum matchType = switch (umlName) {
      case "bool", "boolean" -> PrimitiveTypeEnum.BOOL;
      case "short", "integer", "int" -> PrimitiveTypeEnum.INT;
      case "string" -> PrimitiveTypeEnum.STRING;
      case "real", "float", "double" -> PrimitiveTypeEnum.DOUBLE;
      case "char" -> PrimitiveTypeEnum.CHAR;
      case "byte" -> PrimitiveTypeEnum.BYTE;
      case "long" -> PrimitiveTypeEnum.LONG;
      default -> null;
    };

    if (matchType == null) return null;

    for (PrimitiveDataType type : pcmPrimitiveDataTypes) {
      if (matchType.equals(type.getType())) {
        return type;
      }
    }
    return null;
  }

  public static ParameterDirectionKind getMatchingParameterDirection(ParameterModifier pcmModifier) {
    if (pcmModifier == null || pcmModifier == ParameterModifier.IN || pcmModifier == ParameterModifier.NONE) {
      return ParameterDirectionKind.IN_LITERAL;
    }

    switch (pcmModifier) {
      case OUT:
        return ParameterDirectionKind.OUT_LITERAL;
      case INOUT:
        return ParameterDirectionKind.INOUT_LITERAL;
      default:
        return ParameterDirectionKind.IN_LITERAL;
    }
  }

  public static boolean isContainedInRepositoryHierarchy(org.eclipse.uml2.uml.Package pkg,
                                                         CorrespondenceRetriever correspondenceRetriever) {
    org.eclipse.uml2.uml.Package parentPkg = pkg.getNestingPackage();
    while (parentPkg != null) {
      if (isRepositoryPackage(parentPkg, correspondenceRetriever)) {
        return true;
      }
      parentPkg = parentPkg.getNestingPackage();
    }
    return false;
  }

  private static boolean isRepositoryPackage(org.eclipse.uml2.uml.Package pkg,
                                             CorrespondenceRetriever correspondenceRetriever) {
    EObject corresponding = correspondenceRetriever.retrieveCorrespondingElement(
            pkg, Repository.class, TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE);
    return corresponding != null;
  }

  public static DataType getCorrespondingPcmDataType(Type umlType, int lower, int upper,
                                                     Repository pcmRepository,
                                                     UserInteractor userInteractor,
                                                     CorrespondenceRetriever correspondenceRetriever) {
    if (umlType == null) return null;

    DataType result = (DataType) correspondenceRetriever.retrieveCorrespondingElement(
            umlType, PrimitiveDataType.class, TagLiterals.DATATYPE__TYPE);

    if (result == null) {
      result = (DataType) correspondenceRetriever.retrieveCorrespondingElement(
              umlType, PrimitiveDataType.class, TagLiterals.DATATYPE__TYPE__ALTERNATIVE);
    }

    if (result == null) {
      result = (DataType) correspondenceRetriever.retrieveCorrespondingElement(
              umlType, CompositeDataType.class, TagLiterals.COMPOSITE_DATATYPE__CLASS);
    }

    if (result == null) {
      userInteractor.getNotificationDialogBuilder()
              .message("The uml::Type " + umlType + " could not be resolved to a corresponding pcm::DataType.")
              .notificationType(UserInteractionOptions.NotificationType.WARNING)
              .startInteraction();
      return null;
    }

    if (lower == 0 && upper == LiteralUnlimitedNatural.UNLIMITED) {
      for (DataType dt : pcmRepository.getDataTypes__Repository()) {
        if (dt instanceof CollectionDataType cdt && Objects.equals(cdt.getInnerType_CollectionDataType(), result)) {
          return cdt;
        }
      }

      CollectionDataType newCollection = RepositoryFactory.eINSTANCE.createCollectionDataType();
      newCollection.setEntityName(umlType.getName() + "_CollectionDataType");
      newCollection.setInnerType_CollectionDataType(result);
      pcmRepository.getDataTypes__Repository().add(newCollection);
      return newCollection;
    }

    return result;
  }

  public static String getCorrespondingPackageName(Entity entity) {
    return StringExtensions.toFirstLower(entity.getEntityName());
  }

  public static boolean isPackageFor(org.eclipse.uml2.uml.Package pkg, Entity entity) {
    boolean isRootLevel;

    if (entity instanceof org.palladiosimulator.pcm.repository.Repository ||
            entity instanceof org.palladiosimulator.pcm.system.System) {
      isRootLevel = pkg.eContainer() instanceof Model;
    } else {
      isRootLevel = true;
    }

    return isRootLevel &&
            Objects.equals(pkg.getName().toLowerCase(), entity.getEntityName().toLowerCase());
  }


  private void PcmUmlClassHelper() {
  }
}
