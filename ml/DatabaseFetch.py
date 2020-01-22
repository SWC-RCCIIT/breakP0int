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

print(data)

with open("Data.pickle", "wb") as f:
    pickle.dump(data, f)
print("Pickle Dump Complete.")