import test_accuracyKNN
import pickle, pymongo

pickle_dump = open("medicines.pickle", "rb")
medicines = pickle.load(pickle_dump)

client = pymongo.MongoClient("mongodb+srv://quinn:9163@clusterone-0rkvj.mongodb.net/admin?retryWrites=true&w=majority")
db = client["reelitin"]
col = db["drugschemas"]

print(medicines)

for medicine in medicines:
    accuracy = test_accuracyKNN.test_accuracy(medicine)
    #report = {"drug": medicine, "effect": float(accuracy)}
    #reply = col.insert_one(report)
    print("=====================================")
