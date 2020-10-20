package com.lujunyu.xml.jaxb;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.lujunyu.xml.jaxb.Channel;
import com.lujunyu.xml.jaxb.ObjectFactory;
import com.lujunyu.xml.jaxb.Root;

public class ParseXml {
  public static void main(String[] args) throws JAXBException {
    marshall();
    // Unmarshall();
  }

  private static void marshall() {
    ObjectFactory factory = new ObjectFactory();
    Root root = factory.createRoot();
    Channel channel = factory.createChannel();
    channel.setId("12");
    channel.setProtocol("TCP");
    channel.setSync(false);
    root.setChannel(channel);
    channel.setReq("req");
    channel.setResp("resp");
    try {
      String path =
          Thread.currentThread().getContextClassLoader().getResource("").getPath()
              + File.separator
              + "xml/processes/process.xml";
      File file = new File(path);
      JAXBContext jaxbContext = JAXBContext.newInstance(Root.class);
      Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
      // output pretty printed
      jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      jaxbMarshaller.marshal(root, file);
      jaxbMarshaller.marshal(root, System.out);
    } catch (JAXBException e) {
      e.printStackTrace();
    }
  }

  private static void Unmarshall() throws JAXBException {
    String path =
        Thread.currentThread().getContextClassLoader().getResource("").getPath()
            + File.separator
            + "xml/processes/process.xml";
    File file = new File(path);
    JAXBContext jaxbContext = JAXBContext.newInstance(Root.class);
    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    Root customer = (Root) jaxbUnmarshaller.unmarshal(file);
    System.out.println(customer);
  }
}
