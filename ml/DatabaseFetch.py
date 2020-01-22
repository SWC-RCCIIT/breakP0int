import numpy as np
import pandas as pd
import sklearn, pickle, pymongo
from sklearn import neighbors
from sklearn.model_selection import train_test_split

# Connecting to the MongoDB Database
connectionURL = "mongodb+srv://quinn:9163@clusterone-0rkvj.mongodb.net/admin?retryWrites=true&w=majority"
client = pymongo.MongoClient(connectionURL)
db = client["reelitin"]
print("Connected to Database.")

# Acquiring The Training Data/Collection
collection = db['dataschemas']
data = pd.DataFrame(list(collection.find()))

del data['_id']
del data['__v']

#print(data)

region0 = []

for region, df_region in data.groupby('medicine'):
    #print(region)
    #print(df_region)
    region0.append(region)
    with open(region, "wb") as f:
        pickle.dump(df_region, f)

with open("Data.pickle", "wb") as f:
    pickle.dump(data, f)

with open("medicines.pickle", "wb") as f:
    pickle.dump(region0, f)
print("Pickle Dump Complete.")