/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jop;

import org.apache.jena.ontology.AllValuesFromRestriction;
import org.apache.jena.ontology.AnnotationProperty;
import org.apache.jena.ontology.CardinalityRestriction;
import org.apache.jena.ontology.ComplementClass;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.EnumeratedClass;
import org.apache.jena.ontology.FunctionalProperty;
import org.apache.jena.ontology.HasValueRestriction;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.IntersectionClass;
import org.apache.jena.ontology.InverseFunctionalProperty;
import org.apache.jena.ontology.MaxCardinalityRestriction;
import org.apache.jena.ontology.MinCardinalityRestriction;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.ontology.Restriction;
import org.apache.jena.ontology.SomeValuesFromRestriction;
import org.apache.jena.ontology.SymmetricProperty;
import org.apache.jena.ontology.TransitiveProperty;
import org.apache.jena.ontology.UnionClass;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
//import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import java.io.*;
import java.util.Iterator;

/**
 *
 * @author daveti
 * @date Oct 25, 2012
 * @email daveti@cs.uoregon.edu
 * @blog http://daveti.blog.com
 * 
 * @modifier Jeremy
 * @update_date Feb 28, 2019
 */
public class JenaOWLParser {

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		// Create ontology model using jena
		// Support OWL Full and no reasoning added
		OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		// System.out.println( "ontModel=" + ontModel);
		// Read the input OWL file
		try {
			ontModel.read(new FileInputStream("F:/Dev/workspace/JenaOWLParser/owlfiles/chinesemedicine.owl"), "");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		// Get the base model
		Model baseModel = ontModel.getBaseModel();
		// System.out.println( "baseModel=" + baseModel);
		// Go thru the classes
		// Please refer to OntClass.java from jena API
		System.out.println("======= Class =======");
		collectClassInfo(ontModel);
		// Go thru the properties
		// Please refer to OntProperty.java from jena API
		System.out.println("======= Property =======");
		collectPropertyInfo(ontModel);

		// Go thru the individuals
		// Please refer to Individual.java from jena API
		System.out.println("======= Individual =======");
		collectIndividualInfo(ontModel);
	}

	public static void collectClassInfo(OntModel ontModel) {
		OntClass ontClass;
		int counter = 0;
		Iterator<OntClass> itrClass = ontModel.listClasses();
		while (itrClass.hasNext()) {
			ontClass = itrClass.next();
			counter++;
			System.out.println("------- NO." + counter + " -------");
			// Parse the class
			parseClass(ontClass);
			System.out.println("---------------------");
		}
		System.out.println("======= " + counter + " =======");
	}

	public static void collectPropertyInfo(OntModel ontModel) {
		OntProperty ontProperty;
		int counter = 0;
		Iterator<OntProperty> itrProperty = ontModel.listAllOntProperties();
		while (itrProperty.hasNext()) {
			ontProperty = itrProperty.next();
			counter++;
			System.out.println("------- NO." + counter + " -------");
			// Parse the property
			parseProperty(ontProperty);
			System.out.println("---------------------");
		}
		System.out.println("======= " + counter + " =======");
	}

	public static void collectIndividualInfo(OntModel ontModel) {
		Individual individual;
		int counter = 0;
		Iterator<Individual> itrIndividual = ontModel.listIndividuals();
		while (itrIndividual.hasNext()) {
			individual = itrIndividual.next();
			counter++;
			System.out.println("------- NO." + counter + " -------");
			// Parse the property
			parseIndividual(individual);
			System.out.println("---------------------");
		}
		System.out.println("======= " + counter + " =======");
	}

	public static void parseIndividual(Individual individual) {
		OntClass ontClassTmp;
		System.out.println("NameSpace=" + individual.getNameSpace());
		System.out.println("LocalName=" + individual.getLocalName());
		System.out.println("URI=" + individual.getURI());
		// Check for belonging class
		Iterator<? extends OntClass> itrClass = individual.listOntClasses(true);
		if (itrClass != null) {
			boolean hasClass = itrClass.hasNext();
			if (hasClass == true) {
				System.out.println("Belongs to Class:");
				while (itrClass.hasNext()) {
					ontClassTmp = itrClass.next();
					System.out.println("Class URI=" + ontClassTmp.getURI());
				}
			}
		}
	}

	public static void parseProperty(OntProperty ontProperty) {
		OntClass ontClassTmp;
		OntProperty ontPropTmp;
		OntResource ontResourceTmp;
		Restriction restrictionTmp;

		// Parse different types accordingly
		if (ontProperty.isObjectProperty() == true) {
			// Parse this Object Property
			ObjectProperty objectProp = ontProperty.asObjectProperty();
			System.out.println("-This is an Object Property begins");
		}
		if (ontProperty.isDatatypeProperty() == true) {
			// Parse this Datatype Property
			DatatypeProperty datatypeProp = ontProperty.asDatatypeProperty();
			System.out.println("-This is an Datatype Property begins");
		}
		if (ontProperty.isAnnotationProperty() == true) {
			// Parse this Annotation Property
			AnnotationProperty annotationProp = ontProperty.asAnnotationProperty();
			System.out.println("-This is an Annotation Property begins");
		}

		if (ontProperty.isFunctionalProperty() == true) {
			// Parse this Functional Property
			FunctionalProperty functionalProp = ontProperty.asFunctionalProperty();
			System.out.println("-Characteristics Functional Property");
		}

		if (ontProperty.isInverseFunctionalProperty() == true) {
			// Parse this Inverse Functional Property
			InverseFunctionalProperty inverseFunctionalProp = ontProperty.asInverseFunctionalProperty();
			System.out.println("-Characteristics Inverse Functional Property");
		}
		if (ontProperty.isSymmetricProperty() == true) {
			// Parse this Symmetric Property
			SymmetricProperty symmetricProp = ontProperty.asSymmetricProperty();
			System.out.println("-Characteristics Symmetric Property");
		}
		if (ontProperty.isTransitiveProperty() == true) {
			// Parse this Transitive Property
			TransitiveProperty transitiveProp = ontProperty.asTransitiveProperty();
			System.out.println("-Characteristics Transitive Property");
		}

		// Display the basic info for this Property
		displayBasicInfoForProperty(ontProperty);
		// Check the kind info for this Property
		displayKindInfoForProperty(ontProperty);
		// Check for domain
		Iterator<? extends OntResource> itrDomain = ontProperty.listDomain();
		if (itrDomain != null) {
			boolean hasDomain = itrDomain.hasNext();
			System.out.println("HasDomain=" + hasDomain);
			if (hasDomain == true) {
				System.out.println("Domains begin:");
				while (itrDomain.hasNext()) {
					ontResourceTmp = itrDomain.next();
					displayBasicInfoForResource(ontResourceTmp);
				}
				System.out.println("Domains end");
			}
		}

		// Check for range
		Iterator<? extends OntResource> itrRange = ontProperty.listRange();
		if (itrRange != null) {
			boolean hasRange = itrRange.hasNext();
			System.out.println("HasRange=" + hasRange);
			if (hasRange == true) {
				System.out.println("Ranges begin:");
				while (itrRange.hasNext()) {
					ontResourceTmp = itrRange.next();
					displayBasicInfoForResource(ontResourceTmp);
				}
				System.out.println("Ranges ends");
			}
		}

		// Check if has super properties
		Iterator<? extends OntProperty> itrSuperProp = ontProperty.listSuperProperties();
		if (itrSuperProp != null) {
			boolean hasSuperProperty = itrSuperProp.hasNext();
			System.out.println("HasSuperProperty=" + hasSuperProperty);
			if (hasSuperProperty == true) {
				System.out.println("-Super Properties begin:");
				while (itrSuperProp.hasNext()) {
					ontPropTmp = itrSuperProp.next();
					displayBasicInfoForProperty(ontPropTmp);
				}
				System.out.println("-Super Properties end");
			}
		}

		// Check if has sub properties
		Iterator<? extends OntProperty> itrSubProp = ontProperty.listSubProperties();
		if (itrSubProp != null) {
			boolean hasSubProperty = itrSubProp.hasNext();
			System.out.println("HasSubProperty=" + hasSubProperty);
			if (hasSubProperty == true) {
				System.out.println("-Sub Properties begin:");
				while (itrSubProp.hasNext()) {
					ontPropTmp = itrSubProp.next();
					displayBasicInfoForProperty(ontPropTmp);
				}
				System.out.println("-Sub Properties end");
			}
		}

		// Check if has equivalent properties
		Iterator<? extends OntProperty> itrEquivalentProp = ontProperty.listEquivalentProperties();
		if (itrEquivalentProp != null) {
			boolean hasEquivalentProperty = itrEquivalentProp.hasNext();
			System.out.println("HasEquivalentProperty=" + hasEquivalentProperty);
			if (hasEquivalentProperty == true) {
				System.out.println("-Equivalent Properties beign");
				while (itrEquivalentProp.hasNext()) {
					ontPropTmp = itrEquivalentProp.next();
					displayBasicInfoForProperty(ontPropTmp);
				}
				System.out.println("-Equivalent Properties end");
			}
		}

		// Check if has inverseOf properties
		Iterator<? extends OntProperty> itrInverseOfProp = ontProperty.listInverseOf();
		if (itrInverseOfProp != null) {
			boolean hasInverseOfProperty = itrInverseOfProp.hasNext();
			System.out.println("HasInverseOfProperty=" + hasInverseOfProperty);
			if (hasInverseOfProperty == true) {
				System.out.println("-InverseOf Properties beign:");
				while (itrInverseOfProp.hasNext()) {
					ontPropTmp = itrInverseOfProp.next();
					displayBasicInfoForProperty(ontPropTmp);
				}
				System.out.println("-InverseOf Properties end");
			}
		}

		// Check if has inverse properties
		Iterator<? extends OntProperty> itrInverseProp = ontProperty.listInverse();
		if (itrInverseProp != null) {
			System.out.println("HasInverseProperty=" + ontProperty.hasInverse());
			if (ontProperty.hasInverse()) {
				System.out.println("-Inverse Properties begin:");
				while (itrInverseProp.hasNext()) {
					ontPropTmp = itrInverseProp.next();
					displayBasicInfoForProperty(ontPropTmp);
				}
				System.out.println("-Inverse Properties end");
			}
		}

		// Check if has declaring classes
		Iterator<? extends OntClass> itrDeclaringClass = ontProperty.listDeclaringClasses();
		if (itrDeclaringClass != null) {
			boolean hasDeclaringClass = itrDeclaringClass.hasNext();
			System.out.println("HasDeclaringClass=" + hasDeclaringClass);
			if (hasDeclaringClass == true) {
				System.out.println("-Declaring Classes begin:");
				while (itrDeclaringClass.hasNext()) {
					ontClassTmp = itrDeclaringClass.next();
					displayBasicInfoForClass(ontClassTmp);
				}
				System.out.println("-Declaring Classes end");
			}
		}

		// Check if has referring restrictions
		Iterator<Restriction> itrReferringRestriction = ontProperty.listReferringRestrictions();
		if (itrReferringRestriction != null) {
			boolean hasReferringRestriction = itrReferringRestriction.hasNext();
			System.out.println("HasReferringRestriction=" + hasReferringRestriction);
			if (hasReferringRestriction == true) {
				System.out.println("-Referring Restrictions begin");
				while (itrReferringRestriction.hasNext()) {
					restrictionTmp = itrReferringRestriction.next();
					displayAllInfoForRestriction(restrictionTmp);
				}
				System.out.println("-Referring Restrictions end");
			}
		}

		// Parse different types accordingly
		if (ontProperty.isObjectProperty() == true) {
			// Parse this Object Property
			System.out.println("-The above Object Property ends");
		}
		if (ontProperty.isDatatypeProperty() == true) {
			// Parse this Datatype Property
			System.out.println("-The above Datatype Property ends");
		}
		if (ontProperty.isAnnotationProperty() == true) {
			// Parse this Annotation Property
			System.out.println("The above Annotation Property ends");
		}
		if (ontProperty.isFunctionalProperty() == true) {
			// Parse this Functional Property
			System.out.println("-Characteristics Functional Property ends");
		}

		if (ontProperty.isInverseFunctionalProperty() == true) {
			// Parse this Inverse Functional Property
			System.out.println("-Characteristics Inverse Functional Property ends");
		}
		if (ontProperty.isSymmetricProperty() == true) {
			// Parse this Symmetric Property
			System.out.println("-Characteristics Symmetric Property ends");
		}
		if (ontProperty.isTransitiveProperty() == true) {
			// Parse this Transitive Property
			System.out.println("-Characteristics Transitive Property ends");
		}
	}

	public static void parseClass(OntClass ontClass) {
		OntClass ontSuperClass;
		OntClass ontSubClass;
		OntClass ontEquivalentClass;
		OntClass ontDisjointWithClass;
		OntClass ontClassTmp;
		OntResource ontResourceTmp;
		OntProperty ontDeclaredProperty;
		// Print the basic info - namespace, localName, URI...
		displayBasicInfoForClass(ontClass);
		// Check if has super classes
		boolean hasSuperClasses = ontClass.hasSuperClass();
		System.out.println("HasSuperClass=" + hasSuperClasses);
		if (hasSuperClasses == true) {
			System.out.println("-Super Classes begin:");
			// Display the basic info for all the super classes
			Iterator<OntClass> itrSuperClass = ontClass.listSuperClasses();
			while (itrSuperClass.hasNext()) {
				ontSuperClass = itrSuperClass.next();
				displayBasicInfoForClass(ontSuperClass);
			}
			System.out.println("-Super Classes end");
		}
		// Check if has sub classes
		boolean hasSubClasses = ontClass.hasSubClass();
		System.out.println("HasSubClass=" + hasSubClasses);
		if (hasSubClasses == true) {
			System.out.println("-Sub Classes begin:");
			// Display the basic info for all the sub classes
			Iterator<OntClass> itrSubClass = ontClass.listSubClasses();
			while (itrSubClass.hasNext()) {
				ontSubClass = itrSubClass.next();
				displayBasicInfoForClass(ontSubClass);
			}
			System.out.println("-Sub Classes end");
		}
		// Check if has equivalent classes
		// No boolean hasMethod without any parameter to call directly...
		Iterator<OntClass> itrEquivalentClass = ontClass.listEquivalentClasses();
		System.out.println("HasEquivalentClass=" + itrEquivalentClass.hasNext());
		if (itrEquivalentClass.hasNext()) {
			System.out.println("-Equivalent Classes begin:");
			// Display the basic info for all the equivalent classes
			while (itrEquivalentClass.hasNext()) {
				ontEquivalentClass = itrEquivalentClass.next();
				displayBasicInfoForClass(ontEquivalentClass);
			}
			System.out.println("-Equivalent Classes end");
		}
		// Check if has disjointWith classes
		// No boolean hasMethod without any parameter either...
		Iterator<OntClass> itrDisjointWithClass = ontClass.listDisjointWith();
		System.out.println("HasDisjointWithClass=" + itrDisjointWithClass.hasNext());
		if (itrDisjointWithClass.hasNext()) {
			System.out.println("-DisjointWith Classes begin:");
			// Display the basic info for all the disjointWith classes
			while (itrDisjointWithClass.hasNext()) {
				ontDisjointWithClass = itrDisjointWithClass.next();
				displayBasicInfoForClass(ontDisjointWithClass);
			}
			System.out.println("-DisjointWith Classes end");
		}
		// Check if has declared properties
		// No boolean hasMethod without any parameter either
		Iterator<OntProperty> itrDeclaredProperty = ontClass.listDeclaredProperties();
		System.out.println("HasDeclaredProperty=" + itrDeclaredProperty.hasNext());
		if (itrDeclaredProperty.hasNext()) {
			System.out.println("-Declared Properties begin");
			// Display the basic info for all the declared properties
			while (itrDeclaredProperty.hasNext()) {
				ontDeclaredProperty = itrDeclaredProperty.next();
				displayBasicInfoForProperty(ontDeclaredProperty);
			}
			System.out.println("-Declared Properties end");
		}
		// Check if has instance
		// No boolean hasMethod without any parameter either
		Iterator<? extends OntResource> itrInstance = ontClass.listInstances();
		System.out.println("HasInstance=" + itrInstance.hasNext());
		if (itrInstance.hasNext()) {
			System.out.println("-Instances begin");
			// Display the basic info for all the instances
			while (itrInstance.hasNext()) {
				ontResourceTmp = itrInstance.next();
				displayBasicInfoForResource(ontResourceTmp);
			}
			System.out.println("-Instances end");
		}
		// Check what kind of class for detail
		displayKindInfoForClass(ontClass);
		// Check and handle each kind of class accordingly
		if (ontClass.isEnumeratedClass()) {
			// Handle enumerated class
			EnumeratedClass enumeratedClass = ontClass.asEnumeratedClass();
			System.out.println("-Enumerated Class begins");
			Iterator<? extends OntResource> itrEnumClass = enumeratedClass.listOneOf();
			while (itrEnumClass.hasNext()) {
				ontResourceTmp = itrEnumClass.next();
				System.out.println("--IsOneOf begins");
				displayBasicInfoForResource(ontResourceTmp);
				System.out.println("--IsOneOf ends");
			}
			System.out.println("-Enumerated Class ends");
		}
		if (ontClass.isUnionClass()) {
			// Handle union class
			UnionClass unionClass = ontClass.asUnionClass();
			System.out.println("-Union Class begins");
			Iterator<? extends OntClass> itrUnionClass = unionClass.listOperands();
			while (itrUnionClass.hasNext()) {
				ontClassTmp = itrUnionClass.next();
				System.out.println("--Operand begins");
				displayBasicInfoForClass(ontClassTmp);
				System.out.println("--Operand ends");
			}
			System.out.println("-Union Class ends");
		}
		if (ontClass.isIntersectionClass()) {
			// Handle intersection class
			IntersectionClass intersectionClass = ontClass.asIntersectionClass();
			System.out.println("-Intersection Class begins");
			Iterator<? extends OntClass> itrInterClass = intersectionClass.listOperands();
			while (itrInterClass.hasNext()) {
				ontClassTmp = itrInterClass.next();
				System.out.println("--Operand begins");
				displayBasicInfoForClass(ontClassTmp);
				System.out.println("--Operand ends");
			}
			System.out.println("-Intersection Class ends");
		}
		if (ontClass.isComplementClass()) {
			// Handle complement class
			ComplementClass complementClass = ontClass.asComplementClass();
			System.out.println("-Complement Class begins");
			ontClassTmp = complementClass.getOperand();
			displayBasicInfoForClass(ontClassTmp);
			System.out.println("-Complement Class ends");
		}
		if (ontClass.isRestriction()) {
			// Handle restriction
			Restriction restriction = ontClass.asRestriction();
			System.out.println("-Restriction begins");
			displayAllInfoForRestriction(restriction);
			System.out.println("-Restriction ends");
		}
	}

	public static void parseRestriction(Restriction restriction) {
		Resource resource;
		RDFNode rdfNode;
		int cardinalityInt;
		OntProperty ontProp = restriction.getOnProperty();
		if (ontProp != null) {
			System.out.println("--onPropertyRestriction begins:");
			displayBasicInfoForProperty(ontProp);
			System.out.println("--onPropertyRestriction ends");
		}
		if (restriction.isAllValuesFromRestriction()) {
			// allValues Restriction
			AllValuesFromRestriction allValues = restriction.asAllValuesFromRestriction();
			resource = allValues.getAllValuesFrom();
			System.out.println("--allValuesFromRestriction begins:");
			// Display the basic info for this Resource
			displayBasicInfoForResourceSuper(resource);
			System.out.println("--allValuesFromRestriction ends");
		}
		if (restriction.isSomeValuesFromRestriction()) {
			// someValues Restriction
			SomeValuesFromRestriction someValues = restriction.asSomeValuesFromRestriction();
			resource = someValues.getSomeValuesFrom();
			System.out.println("--someValuesRestriction begins:");
			// Display the basic info for this Resource
			displayBasicInfoForResourceSuper(resource);
			System.out.println("--someValuesRestriction ends");
		}
		if (restriction.isHasValueRestriction()) {
			HasValueRestriction hasValue = restriction.asHasValueRestriction();
			rdfNode = hasValue.getHasValue();
			System.out.println("--hasValueRestriction begins:");
			// Display the basic info for this RDFNode
			// Call the toString method for now
			// TODO: may need to parse this RDFnode into certain type...
			System.out.println(rdfNode);
			System.out.println("--hasValueRestriction ends");
		}
		if (restriction.isCardinalityRestriction()) {
			CardinalityRestriction cardinality = restriction.asCardinalityRestriction();
			cardinalityInt = cardinality.getCardinality();
			System.out.println("--cardinalityRestriction begins:");
			System.out.println(cardinalityInt);
			System.out.println("--cardinalityRestriction ends");
		}
		if (restriction.isMaxCardinalityRestriction()) {
			MaxCardinalityRestriction maxCardinality = restriction.asMaxCardinalityRestriction();
			cardinalityInt = maxCardinality.getMaxCardinality();
			System.out.println("--maxCardinalityRestriction beigns:");
			System.out.println(cardinalityInt);
			System.out.println("--maxCardinalityRestriction ends");
		}
		if (restriction.isMinCardinalityRestriction()) {
			MinCardinalityRestriction minCardinality = restriction.asMinCardinalityRestriction();
			cardinalityInt = minCardinality.getMinCardinality();
			System.out.println("--minCardinalityRestriction begins:");
			System.out.println(cardinalityInt);
			System.out.println("--minCardinalityRestriction ends");
		}
	}

	public static void displayBasicInfoForClass(OntClass ontClass) {
		// Print the basic info for Class
		// namespace, localName, URI, versionInfo, Id
		if (ontClass.isRestriction() == false) {
			// These methods below are 'null' returned for Restriction
			System.out.println("NameSpace=" + ontClass.getNameSpace());
			System.out.println("LocalName=" + ontClass.getLocalName());
			System.out.println("URI=" + ontClass.getURI());
			// System.out.println( "VersionInfo=" + ontClass.getVersionInfo());
			// System.out.println( "Id=" + ontClass.getId());
			// System.out.println( "isHierarchyRoot=" +
			// ontClass.isHierarchyRoot());
		} else {
			System.out.println("Restrictions:");
			Restriction restriction = ontClass.asRestriction();
			displayAllInfoForRestriction(restriction);
		}
	}

	public static void displayKindInfoForClass(OntClass ontClass) {
		// Print the kind info for Class
		System.out.println("isEnumeratedClass=" + ontClass.isEnumeratedClass());
		System.out.println("isUnionClass=" + ontClass.isUnionClass());
		System.out.println("isIntersectionClass=" + ontClass.isIntersectionClass());
		System.out.println("isComplementClass=" + ontClass.isComplementClass());
		System.out.println("isRestriction=" + ontClass.isRestriction());
	}

	public static void displayBasicInfoForProperty(OntProperty ontProperty) {
		// Print the basic info for Property
		// namespace, localName, URI, versionInfo, domain
		System.out.println("NameSpace=" + ontProperty.getNameSpace());
		System.out.println("LocalName=" + ontProperty.getLocalName());
		System.out.println("URI=" + ontProperty.getURI());
		// System.out.println( "VersionInfo=" + ontProperty.getVersionInfo());
	}

	public static void displayKindInfoForProperty(OntProperty ontProperty) {
		// Print the kind info for this Property
		System.out.println("isFunctionalProperty=" + ontProperty.isFunctionalProperty());
		System.out.println("isDatatypeProperty=" + ontProperty.isDatatypeProperty());
		System.out.println("isObjectProperty=" + ontProperty.isObjectProperty());
		System.out.println("isInverseFunctionalProperty=" + ontProperty.isInverseFunctionalProperty());
		System.out.println("isSymmetricProperty=" + ontProperty.isSymmetricProperty());
		System.out.println("isTransitiveProperty=" + ontProperty.isTransitiveProperty());
	}

	public static void displayBasicInfoForResource(OntResource ontResource) {
		// Print the basic info for Resrouce
		// namespace, localName, URI, versionInfo, domain
		System.out.println("NameSpace=" + ontResource.getNameSpace());
		System.out.println("LocalName=" + ontResource.getLocalName());
		System.out.println("URI=" + ontResource.getURI());
		// System.out.println( "VersionInfo=" + ontResource.getVersionInfo());
	}

	public static void displayBasicInfoForResourceSuper(Resource resource) {
		// Print the basic info for the Super Resource
		System.out.println("NameSpace=" + resource.getNameSpace());
		System.out.println("LocalName=" + resource.getLocalName());
		System.out.println("URI=" + resource.getURI());
	}

	public static void displayKindInfoForRestriction(Restriction restriction) {
		// Refer to Restriction.java from jena API
		OntProperty ontProp = restriction.getOnProperty();
		if (ontProp != null) {
			System.out.println("---onPropertyRestriction=true");
		} else {
			System.out.println("---onPropertyRestriction=false");
		}
		System.out.println("---isAllValuesFromRestriction=" + restriction.isAllValuesFromRestriction());
		System.out.println("---someValuesFromRestriction=" + restriction.isSomeValuesFromRestriction());
		System.out.println("---CardinalityRestriction=" + restriction.isCardinalityRestriction());
		System.out.println("---hasValueRestriction=" + restriction.isHasValueRestriction());
		System.out.println("---isMaxCardinalityRestriction=" + restriction.isMaxCardinalityRestriction());
		System.out.println("---isMinCardinalityRestriction=" + restriction.isMinCardinalityRestriction());
	}

	public static void displayAllInfoForRestriction(Restriction restriction) {
		// Display the kind info as well as Restriction does not have name/URI
		displayKindInfoForRestriction(restriction);
		// Parse the Restriction here
		parseRestriction(restriction);
	}
}
