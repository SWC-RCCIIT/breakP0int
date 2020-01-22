import numpy as np
import pandas as pd
import sklearn, pickle, pymongo
from sklearn import neighbors
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder

# Acquire Data
pickle_dump = open("Data.pickle", "rb")
data = pickle.load(pickle_dump)

print("Acquired Training Data / Collection.")

# Data Pre-processing
le = LabelEncoder()
data['result'] = le.fit_transform(data['result'])
data['disease'] = le.fit_transform(data['disease'])
data['medicine'] = le.fit_transform(data['medicine'])
print(data)
# Setting Features and Labels
print("Setting up Features & Labels.")
X = np.array(data.drop(['result'], 1))
Y = np.array(data['result'])

# Train Test Split
X_train, X_test, Y_train, Y_test = train_test_split(X, Y, test_size=0.25)

classifier = neighbors.KNeighborsClassifier()
classifier.fit(X_train, Y_train)

# Accuracy Testing
accuracy = classifier.score(X_test, Y_test)
print(accuracy)
print(data.shape)