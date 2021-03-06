/*
 *  Java OTR library
 *  Copyright (C) 2008-2009  Ian Goldberg, Muhaimeen Ashraf, Andrew Chung,
 *                           Can Tang
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of version 2.1 of the GNU Lesser General
 *  Public License as published by the Free Software Foundation.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package ca.uwaterloo.crysp.otr.message;

import ca.uwaterloo.crysp.otr.Data;
import ca.uwaterloo.crysp.otr.InBuf;
import ca.uwaterloo.crysp.otr.OTRException;
import ca.uwaterloo.crysp.otr.OutBuf;

public class DHCommitMessage extends OTREncodedMessage {
  private Data encryptedGx; // the encrypted g^X
  private Data hashedGx; // the hashed g^X

  /**
   * Constructor
   * 
   * @param protocolVersion the protocol version
   * @param encrGx the encrypted g^X Data
   * @param hashGx the hashed g^X Data
   */
  public DHCommitMessage(short protocolVersion, Data encrGx, Data hashGx) {
    super(protocolVersion, OTRMessage.MSG_DH_COMMIT);
    this.encryptedGx = encrGx;
    this.hashedGx = hashGx;
  }

  /**
   * Get encrypted Gx
   * 
   * @return encrypted Gx
   */
  public Data getEncryptedGx() {
    return this.encryptedGx;
  }

  /**
   * Get hashed Gx
   * 
   * @return hashed Gx
   */
  public Data getHashedGx() {
    return this.hashedGx;
  }

  /**
   * Read and return a DHCommitMessage object
   * 
   * @param stream input buffer stream to read from
   * @param protocolVersion the protocol version
   * @return DHCommitMessage object
   * @throws OTRException
   */
  public static DHCommitMessage readDHCommitMessage(InBuf stream, short protocolVersion)
      throws OTRException {
    Data encryptedGx = stream.readData();
    Data hashedGx = stream.readData();
    return new DHCommitMessage(protocolVersion, encryptedGx, hashedGx);
  }

  /**
   * Serialize object and write to output buffer stream
   * 
   * @param stream output buffer stream
   * @throws OTRException
   */
  public void write(OutBuf stream) throws OTRException {
    // Write protocol version and message type
    super.write(stream);

    // Write message specific content
    stream.writeData(this.encryptedGx);
    stream.writeData(this.hashedGx);
  }

  public byte[] getContent() throws OTRException {
    OutBuf st = new OutBuf(new byte[1024]);
    write(st);
    return st.getBytes();
  }

  @Override
  public String toString() {
    return "DHCommitMessage [encryptedGx=" + encryptedGx + ", hashedGx=" + hashedGx + "]";
  }
}
